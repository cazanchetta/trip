## Trabalho da disciplina Servless
Professor: Peterson Larentis

Grupo:

* RM 335232 - Carlos Zanchetta
* RM 335798 - Victor Hugo
* RM 336295 - Thiago Veiga
* RM 335826 - Rodrigo Nonato

-----------------------------------------------------------------------------------------------------------

### Requisitos

* AWS CLI already configured with at least PowerUser permission
* [Java SE Development Kit 8 installed](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Docker installed](https://www.docker.com/community-edition)
* [Maven](https://maven.apache.org/install.html)
* [SAM CLI](https://github.com/awslabs/aws-sam-cli)
* [Python 3](https://docs.python.org/3/)


###Aplicação AWS SAM para gerenciamento de viagens


- Efetuar um clone do repositório do github `https://github.com/cazanchetta/trip.git`

- Executar o `mvn install` para gerar os pacotes de dependencias

- Executar o comando para subir uma imagem docker do dynamoDB e acessá-lo local `docker run -p 8000:8000 -v $(pwd)/local/dynamodb:/data/ amazon/dynamodb-local -jar DynamoDBLocal.jar -sharedDb -dbPath /data`

- Executar o comando para criar a tabela trip `aws dynamodb create-table --table-name trip --attribute-definitions AttributeName=country,AttributeType=S AttributeName=date,AttributeType=S AttributeName=city,AttributeType=S AttributeName=reason,AttributeType=S --key-schema AttributeName=country,KeyType=HASH AttributeName=date,KeyType=RANGE --local-secondary-indexes 'IndexName=cityIndex,KeySchema=[{AttributeName=country,KeyType=HASH},{AttributeName=city,KeyType=RANGE}],Projection={ProjectionType=ALL}' 'IndexName=reasonIndex,KeySchema=[{AttributeName=country,KeyType=HASH},{AttributeName=reason,KeyType=RANGE}],Projection={ProjectionType=ALL}' --billing-mode PAY_PER_REQUEST --endpoint-url http://localhost:8000`

- Caso queira excluir a tabela, executar o comando `aws dynamodb delete-table --table-name trip --endpoint-url http://localhost:8000`

- Rode os comandos abaixo, de acordo com o sistema operacional, para rodar o sam local

	Mac: sam local start-api --env-vars src/test/resources/test_environment_mac.json

	Windows: sam local start-api --env-vars src/test/resources/test_environment_windows.json

	Linux: sam local start-api --env-vars src/test/resources/test_environment_linux.json

- No diretório /test/resoruces do projeto, existe o arquivo Trip.postman_collection.json, importe esse arquivo como collection no postman para consumir os endpoints localmente.
