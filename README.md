# FIAP - Serverless: Trabalho Final

**Turma:** 35SCJ
**Professor:** Peterson Larentis
**Grupo:**
* 335232 - Carlos Zanchetta
* 335798 - Victor Hugo
* 336295 - Thiago Veiga
* 335826 - Rodrigo Nonato

## Requisitos
1) [AWS CLI](https://docs.aws.amazon.com/cli/latest/userguide/install-cliv2.html) já configurada com pelo menos permissões de "Power User"
2) [JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) instalado
3) [Docker](https://www.docker.com/community-edition) instalado
4) [Maven](https://maven.apache.org/install.html)
5) [SAM CLI](https://github.com/awslabs/aws-sam-cli)
6) [Python 3](https://docs.python.org/3/)

## Aplicação AWS SAM para Gerenciamento de Viagens
1. Clonar o repositório do **_GitHub_**:
    ```shell script
    git clone https://github.com/cazanchetta/trip.git
    ```

2. Instalar o pacote e suas dependências no ambiente local utilizando **_Maven_**:
    ```shell script
    mvn clean install -X -U
    ```

3. Subir localmente uma **Imagem Docker** do **_DynamoDB_**:
    ```shell script
    docker run -p 8000:8000 -v $(pwd)/local/dynamodb:/data/ amazon/dynamodb-local -jar DynamoDBLocal.jar -sharedDb -dbPath /data
    ```

4. Criar a tabela "trip" no **_DynamoDB_**:
    ```shell script
    aws dynamodb create-table \
    --table-name trip \
    --attribute-definitions \
        AttributeName=country,AttributeType=S \
        AttributeName=date,AttributeType=S \
        AttributeName=city,AttributeType=S \
    --key-schema \
        AttributeName=country,KeyType=HASH \
        AttributeName=date,KeyType=RANGE  \
    --local-secondary-indexes \
        'IndexName=cityIndex,KeySchema=[{AttributeName=country,KeyType=HASH},{AttributeName=city,KeyType=RANGE}],Projection={ProjectionType=ALL}' \
    --billing-mode PAY_PER_REQUEST \
    --endpoint-url http://localhost:8000
    ```

    * **Observação:** Caso queira excluir a tabela, execute o comando:
        ```shell script
        aws dynamodb delete-table --table-name trip --endpoint-url http://localhost:8000
        ```

5. Executar o **_SAM_** localmente (vide scripts, conforme SO):
    * **Mac:**
        ```shell script
        sam local start-api --env-vars src/test/resources/test_environment_mac.json
        ```
    * **Windows:**
        ```shell script
        sam local start-api --env-vars src/test/resources/test_environment_windows.json
        ```
    * **Linux:**
        ```shell script
        sam local start-api --env-vars src/test/resources/test_environment_linux.json
        ```
6. Importe o arquivo `/test/resources/Trip.postman_collection.json` como collection no **Postman** para consumir os endpoints da aplicação.

## Empacotar e subir aplicação para o AWS S3
1. Criar um **_Bucket_** no **S3**:
    ```shell script
    export BUCKET_NAME=fiap-servless-trip-35scj && aws s3 mb s3://$BUCKET_NAME
    ```

2. Empacotar a aplicação e disponibilizá-la no **_Bucket S3_**:
    ```shell script
    sam package \
    --template-file template.yaml \
    --output-template-file packaged.yaml \
    --s3-bucket $BUCKET_NAME
    ```

3. Iniciar o Cloud Formation para criar nossos recursos na **_AWS_**:
    ```shell script
    sam deploy \
    --template-file packaged.yaml \
    --stack-name trip \
    --capabilities CAPABILITY_IAM
    ```