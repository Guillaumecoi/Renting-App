package coigniez.rentapp.interfaces;

public interface MapperInterface<E, D> {

    /**
     * Convert an entity to a DTO
     *
     * @param entity the entity to convert
     * @return the DTO
     */
    public D toDto(E entity);

    /**
     * Convert a DTO to an entity
     *
     * @param dto the DTO to convert
     * @return the entity
     * @throws Exception if the conversion fails
     */
    public E toEntity(D dto) throws Exception;

}
