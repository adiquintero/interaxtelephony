package com.interax.telephony.service.remote.callingcard;

import com.interax.telephony.service.callingcard.data.CallingCardAuthenticateData;
import com.interax.telephony.service.callingcard.data.CallingCardFavoriteDestinationCombination;
import com.interax.telephony.service.callingcard.data.CallingCardPin;
import com.interax.telephony.service.exception.callingcard.CallingCardOverdueInvoiceException;
import com.interax.telephony.service.exception.callingcard.CallingCardCreditLimitExceededException;
import com.interax.telephony.service.exception.callingcard.CallingCardException;
import com.interax.telephony.service.exception.callingcard.CallingCardGeneralException;
import com.interax.telephony.service.exception.callingcard.CallingCardInvalidPinException;
import com.interax.telephony.util.InteraxTelephonyGenericEjb;

import java.util.Date;
import java.util.List;

public interface CallingCardPinEJB extends InteraxTelephonyGenericEjb  {
	
	
	/**
	 * Obtiene una tarjeta de llamadas a partir de la clave secreta o del número telefónico asociado.
	 * @param CallingCardAuthenticateData	callingCardAuthenticateData objeto con un secret o un ani y el tipo de acceso.
	 * @return com.sg123.model.platformprepay.Pin
	 * @throws CallingCardCreditLimitExceededException
	 * @throws CallingCardNotMinBalanceException
	 * @throws CallingCardGeneralException
         * @throws CallingCardOverdueInvoiceException
	 */
	public CallingCardPin searchPin(CallingCardAuthenticateData callingCardAuthenticateData) throws CallingCardException;
	
	/**
	 * Cambia el password de un pin
	 * @param serial	Número o serial de la tarjeta.
	 * @return Boolean. true si se cambia el password con exito, false en caso contrario
	 * @throws CallingCardInvalidPinException
	 * @throws CallingCardGeneralException
	 */
	public boolean updatePinPassword(Long serial, String md5) throws CallingCardException;

     /**
	 * Obtiene un número de acceso para el servicio y el ani dados
	 * @param serviceId ID del servicio del lado del SISAC
         * @param ani Ani para la búsqueda del número de acceso
	 * @return String. El número de acceso obtenido
	 * @throws CallingCardGeneralException
	 */
	
	public boolean updatePinExpirationDate(Long serial, Date firstUseDate, Long expirationDays) throws CallingCardException ;
	
	 /**
		 * Actualiza la fecha en que expira un pin. Usado para tarjetas físicas .
		 * @param serial long  de la tarjeta
	     * @param firstUseDate date. Primera vez que se utiliza el pin dado
		 * @return expirationDays long. String. En cuantos días se utiliza un pin. 
		 * @throws CallingCardGeneralException
		 */
	
    public String getAccessNumberByAni(Long serviceId, String ani) throws CallingCardException;

        /**
	 * Obtiene los números de acceso para el servicio y el ani dados
	 * @param serviceId ID del servicio del lado del SISAC
         * @param ani Ani para la búsqueda del número de acceso
	 * @return List<String>. Los números de acceso obtenidos
	 * @throws CallingCardGeneralException
	 */
        public List<String> getAccessNumbersByAni(Long serviceId, String ani, Long quantity) throws CallingCardException;
        
        
        /**
    	 * Obtiene el numero favorito asociado al ani llamado
    	 * @param ani del cliente del lado del SISAC
             * @param ani Ani para la búsqueda del número de acceso
    	 * @return List<String>. Los números favoritos obtenidos
         * @throws  
         * @throws GeneralException 
         * @throws NullParameterException 
         * @throws NumberFormatException 
    	 * @throws CallingCardGeneralException
    	 */
         public CallingCardFavoriteDestinationCombination getFavoriteDestinationByAni(String ani, String accessNumber) throws CallingCardException	;
        
}
