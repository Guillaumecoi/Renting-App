#!/bin/bash

ENTITY=$1
LOWERCASE_ENTITY=$(echo "$ENTITY" | awk '{print tolower($0)}')

mkdir -p ../src/main/java/coigniez/rentapp/model/$LOWERCASE_ENTITY
mkdir -p ../src/test/java/coigniez/rentapp/model/$LOWERCASE_ENTITY

echo "Creating files for entity: $ENTITY"

# Create Entity
ENTITY_TEMPLATE=$(cat templates/entity.txt)
ENTITY_TEMPLATE=${ENTITY_TEMPLATE//\$ENTITY/$ENTITY}
ENTITY_TEMPLATE=${ENTITY_TEMPLATE//\$LOWERCASE_ENTITY/$LOWERCASE_ENTITY}
echo "$ENTITY_TEMPLATE" > ../src/main/java/coigniez/rentapp/model/$LOWERCASE_ENTITY/$ENTITY.java

# Create Repository
REPOSITORY_TEMPLATE=$(cat templates/repository.txt)
REPOSITORY_TEMPLATE=${REPOSITORY_TEMPLATE//\$ENTITY/$ENTITY}
REPOSITORY_TEMPLATE=${REPOSITORY_TEMPLATE//\$LOWERCASE_ENTITY/$LOWERCASE_ENTITY}
echo "$REPOSITORY_TEMPLATE" > ../src/main/java/coigniez/rentapp/model/$LOWERCASE_ENTITY/${ENTITY}Repository.java

# Create Service
SERVICE_TEMPLATE=$(cat templates/service.txt)
SERVICE_TEMPLATE=${SERVICE_TEMPLATE//\$ENTITY/$ENTITY}
SERVICE_TEMPLATE=${SERVICE_TEMPLATE//\$LOWERCASE_ENTITY/$LOWERCASE_ENTITY}
echo "$SERVICE_TEMPLATE" > ../src/main/java/coigniez/rentapp/model/$LOWERCASE_ENTITY/${ENTITY}Service.java

# Create Test for Entity
ENTITY_TEST_TEMPLATE=$(cat templates/entity_test.txt)
ENTITY_TEST_TEMPLATE=${ENTITY_TEST_TEMPLATE//\$ENTITY/$ENTITY}
ENTITY_TEST_TEMPLATE=${ENTITY_TEST_TEMPLATE//\$LOWERCASE_ENTITY/$LOWERCASE_ENTITY}
echo "$ENTITY_TEST_TEMPLATE" > ../src/test/java/coigniez/rentapp/model/$LOWERCASE_ENTITY/${ENTITY}Test.java

# Create Test for Repository
REPOSITORY_TEST_TEMPLATE=$(cat templates/repository_test.txt)
REPOSITORY_TEST_TEMPLATE=${REPOSITORY_TEST_TEMPLATE//\$ENTITY/$ENTITY}
REPOSITORY_TEST_TEMPLATE=${REPOSITORY_TEST_TEMPLATE//\$LOWERCASE_ENTITY/$LOWERCASE_ENTITY}
echo "$REPOSITORY_TEST_TEMPLATE" > ../src/test/java/coigniez/rentapp/model/$LOWERCASE_ENTITY/${ENTITY}RepositoryTest.java

# Create Test for Service
SERVICE_TEST_TEMPLATE=$(cat templates/service_test.txt)
SERVICE_TEST_TEMPLATE=${SERVICE_TEST_TEMPLATE//\$ENTITY/$ENTITY}
SERVICE_TEST_TEMPLATE=${SERVICE_TEST_TEMPLATE//\$LOWERCASE_ENTITY/$LOWERCASE_ENTITY}
echo "$SERVICE_TEST_TEMPLATE" > ../src/test/java/coigniez/rentapp/model/$LOWERCASE_ENTITY/${ENTITY}ServiceTest.java