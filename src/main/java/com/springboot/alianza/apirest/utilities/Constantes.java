package com.springboot.alianza.apirest.utilities;

public final class Constantes {

    /**
     * Constantes de la aplicación
     */
    private Constantes() {
        throw new IllegalStateException("Clase de constantes");
    }

    /**
     * Obtener todos los clientes registrados
     */
    public static final String FIND_ALL = "Iniciando método index() para obtener la lista de clientes";
    /**
     * Obtener cliente por identificador
     */
    public static final String FIND_BY_ID = "Recuperando cliente por ID: {}";
    /**
     * Crear cliente
     */
    public static final String SAVE_CLIENT = "Cliente creado con éxito: {}";
    /**
     * Actualizar cliente
     */
    public static final String UPDATE_CLIENT = "Actualizando cliente con ID: {}";
    /**
     * Buscar por sharedKey
     */
    public static final String SHARED_KEY = "Iniciando búsqueda por sharedKey";
    /**
     * Exportar CSV
     */
    public static final String EXPORT_CSV = "Iniciando exportación de clientes a CSV";
    /**
     * Finalización de exportación
     */
    public static final String EXPORT_CSV_COMPLETE = "Exportación de clientes a CSV completada";

}
