package coigniez.rentapp.model.interfaces;

import java.util.List;

public interface Mapper<E, D> {

    /**
     * Convert an entity to a DTO
     * @param entity the entity to convert
     * @return the DTO
     */
    public D entityToDto(E entity);

    /**
     * Convert a list of entities to a list of DTOs
     * @param entities the list of entities to convert
     * @return the list of DTOs
     */
    public List<D> entitiesToDtos(List<E> entities);

    /**
     * Convert a DTO to an entity
     * @param dto the DTO to convert
     * @return the entity
     * @throws Exception if the DTO is invalid
     */
    public E dtoToEntity(D dto) throws Exception;

    /**
     * Convert a list of DTOs to a list of entities
     * @param dtos the list of DTOs to convert
     * @return the list of entities
     * @throws Exception if a DTO is invalid
     */
    public List<E> dtosToEntities(List<D> dtos) throws Exception;
    
}
