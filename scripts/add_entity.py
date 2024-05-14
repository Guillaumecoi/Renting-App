import os
import string

SCRIPT_DIR = os.path.dirname(__file__)
PROJECT_DIR = os.path.dirname(SCRIPT_DIR)

def replace_template(template_path, entity, lowercase_entity):
    template_path = os.path.join(SCRIPT_DIR, template_path)
    with open(template_path, 'r') as file:
        template = file.read()
    return template.replace('$ENTITY', entity).replace('$LOWERCASE_ENTITY', lowercase_entity)

def write_file(path, content):
    with open(path, 'w') as file:
        file.write(content)

def main():
    entity = input("Please enter the entity name: ")
    lowercase_entity = entity.lower()

    model_dir = os.path.join(PROJECT_DIR, f'src/main/java/coigniez/rentapp/model/{lowercase_entity}')
    os.makedirs(model_dir, exist_ok=True)

    test_dir = os.path.join(PROJECT_DIR, f'src/test/java/coigniez/rentapp/model/{lowercase_entity}')
    os.makedirs(test_dir, exist_ok=True)

    print(f"Creating files for entity: {entity}")

    # Create Entity
    entity_template = replace_template('templates/entity.txt', entity, lowercase_entity)
    write_file(os.path.join(model_dir, f'{entity}.java'), entity_template)

    # Create DTO
    dto_template = replace_template('templates/dto.txt', entity, lowercase_entity)
    write_file(os.path.join(model_dir, f'{entity}DTO.java'), dto_template)

    # Create Mapper
    mapper_template = replace_template('templates/mapper.txt', entity, lowercase_entity)
    write_file(os.path.join(model_dir, f'{entity}Mapper.java'), mapper_template)

    # Create Repository
    repository_template = replace_template('templates/repository.txt', entity, lowercase_entity)
    write_file(os.path.join(model_dir, f'{entity}Repository.java'), repository_template)

    # Create Service
    service_template = replace_template('templates/service.txt', entity, lowercase_entity)
    write_file(os.path.join(model_dir, f'{entity}Service.java'), service_template)

    # Creating the test files

    # Create Test for Entity
    entity_test_template = replace_template('templates/entity_test.txt', entity, lowercase_entity)
    write_file(os.path.join(test_dir, f'{entity}Test.java'), entity_test_template)

    # Create Test for Mapper
    mapper_test_template = replace_template('templates/mapper_test.txt', entity, lowercase_entity)
    write_file(os.path.join(test_dir, f'{entity}MapperTest.java'), mapper_test_template)

    # Create Test for Repository
    repository_test_template = replace_template('templates/repository_test.txt', entity, lowercase_entity)
    write_file(os.path.join(test_dir, f'{entity}RepositoryTest.java'), repository_test_template)

    # Create Test for Service
    service_test_template = replace_template('templates/service_test.txt', entity, lowercase_entity)
    write_file(os.path.join(test_dir, f'{entity}ServiceTest.java'), service_test_template)

if __name__ == "__main__":
    main()