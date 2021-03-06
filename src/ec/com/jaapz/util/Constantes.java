package ec.com.jaapz.util;

public abstract class Constantes {
	//para envio de correos
	public static String CORREO_ORIGEN = "+BA5Ade2O7n+w5je1mccSElQ52k/kx49vbLhtU0JAVc=";
	public static String CONTRASENIA_CORREO = "HYp+hqbwITGaQsyO+a/09Q==";
	/*---------------------------------------------------------------------------*/
	public static String ORIGEN_MOVIL = "MOVIL";
	public static String ORIGEN_ESCRITORIO = "ESCRITORIO";
	
	public static int ID_MEDIDOR = 2;
	public static int ID_TASA_CONEXION = 1;

	public static String IDENT_INSTALACION = "INS";
	public static String IDENT_REPARACION = "REP";
	public static String IDENT_ADICIONAL = "ADC";
	public static String IDENT_CONVENIO = "CONV";
	public static String IDENT_PROCESO = "SIN PLANILLAR";
	public static String IDENT_LECTURA = "LEC";
	//id de los diferentes usuarios que estan registrados en el sistema
	public static int ID_USU_ADMINISTRADOR = 1;
	public static int ID_USU_LECTURA = 2;
	public static int ID_USU_INSPECCION = 3;
	public static int ID_USU_PRESIDENTE = 5;
	public static int ID_USU_REPARACIONES = 6;
	public static int ID_USU_SECRETARIA = 7;
	public static int ID_USU_BODEGA = 8;
	public static int ID_USU_RECAUDACIONES = 9;
	public static int ID_USU_INSTALACIONES = 10;
	public static int ID_USU_CORTE_RECONEXIONES = 12;
	
	public static String EST_INSPECCION_REALIZADO = "REALIZADO";
	public static String EST_INSPECCION_PENDIENTE = "PENDIENTE";
	public static String EST_FACTIBLE = "FACTIBLE";
	public static String EST_NO_FACTIBLE = "NO FACTIBLE";
	public static String EST_APERTURA_PROCESO = "EN PROCESO";
	public static String EST_APERTURA_REALIZADO = "REALIZADO";
	public static String CONVENIO_NO = "NO";
	public static String CONVENIO_SI = "SI";
	
	public static String CAT_VIVIENDA = "VIVIENDA";
	public static String CAT_COMERCIAL = "COMERCIAL";
	public static String CAT_ESTABLECIMIENTO = "ESTABLECIMIENTO P�BLICO";
	
	public static String ESTADO_ACTIVO = "A";
	public static String ESTADO_INACTIVO = "I";
	
	public static String EST_FAC_CANCELADO = "CANCELADO";
	public static String EST_FAC_PENDIENTE = "PENDIENTE";
	
	private static final Integer TIPO_RUC_NATURAL= 1;
	private static final Integer RUC_PRIVADA = 2;
	private static final Integer RUC_PUBLICA=3;
	
	public static Integer getTipoRucNatural() {
		return TIPO_RUC_NATURAL;
	}
	public static Integer getRucPrivada() {
		return RUC_PRIVADA;
	}
	public static Integer getRucPublica() {
		return RUC_PUBLICA;
	}
	
	//acciones para la sincronizacion
	public static String BODEGA_INGRESO = "ING";
	public static String BODEGA_SALIDA = "SLDA";
}

