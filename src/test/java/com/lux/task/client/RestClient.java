package com.lux.task.client;

/**
 * @param <T>  type of entity.
 * @param <ID> type of entity ID.
 * @param <R>  type of response.
 */
public interface RestClient<T, ID, R> {

    /**
     * Retrieves only the headers of the response, not the actual body content.
     * Can be used to check the availability of resources.
     */
    R head();

    R readAll();

    R read(ID id);

    R create(T object);

    R update(T object);

    R delete(ID id);
}
