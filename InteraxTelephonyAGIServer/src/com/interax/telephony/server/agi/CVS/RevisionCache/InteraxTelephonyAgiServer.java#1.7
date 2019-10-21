package com.interax.telephony.server.agi;

import com.interax.telephony.service.data.ServiceCancelData;
import com.interax.telephony.service.data.ServiceCommitData;
import com.interax.telephony.service.remote.RaterEJB;
import com.interax.telephony.util.GeneralUtils;
import com.interax.telephony.util.InteraxTelephonyGenericEjbCaller;
import com.interax.utils.FileUtils;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;

import org.asteriskjava.fastagi.DefaultAgiServer;
import org.asteriskjava.fastagi.ResourceBundleMappingStrategy;

public class InteraxTelephonyAgiServer{

	//Logger
	protected InteraxTelephonyAgiServerLogger logger;
	private static final long serialVersionUID = 1L;
	private static InteraxTelephonyAgiServerConfig agiServerConfig;


	protected InteraxTelephonyAgiServer() throws RemoteException {
            Thread.currentThread().setName("INTERAX-TELEPHONY AGI SERVER");
            this.logger = new InteraxTelephonyAgiServerLogger();
            this.logger.info("Initializing AgiServer");
	}
	
	public static InteraxTelephonyAgiServerConfig getConfig(){
		return agiServerConfig;
	}

        private void processPendingReservations() {

            String persistencePath = agiServerConfig.PERSISTENCE_PATH + agiServerConfig.AGIS_CONFIG_PATH;
            String pathSeparator = "/";
            HashMap<String,String[]> serviceMap = new HashMap<String,String[]>();
            serviceMap.put("callingcard",new String[] {"CallingCardAgi"});
            serviceMap.put("ippbx",new String[] {"IpPbxIncomingAgi","IpPbxOutgoingAgi","IpPbxVonAgi"});
            serviceMap.put("pickdialing",new String[] {"PickDialingOutgoingAgi"});
            serviceMap.put("voicetraffic",new String[] {"VoiceTrafficOutgoingAgi"});
            serviceMap.put("did",new String[] {"DidOutgoingAgi"});
            String commitFolder = "commit";
            String cancelFolder = "cancel";
            FileUtils fileManager = new FileUtils();
            InteraxTelephonyGenericEjbCaller genericEjbCaller = new InteraxTelephonyGenericEjbCaller(logger);
            RaterEJB raterEJB = (RaterEJB)genericEjbCaller.getGenericController("ejb/RaterEJB", agiServerConfig.RATER_EJB_SERVER, agiServerConfig.RATER_EJB_PORT);

            Iterator<String> iterator = serviceMap.keySet().iterator();
            File folder;
            File[] listOfFiles;
            String commitPath;
            String cancelPath;
            while(iterator.hasNext()) {
                String serviceFamily = iterator.next();
                for(String agi : serviceMap.get(serviceFamily)) {
                    listOfFiles = null;
                    commitPath = persistencePath + pathSeparator + serviceFamily + pathSeparator + agi + pathSeparator + commitFolder + pathSeparator;
                    logger.info("Checking commit files in "+commitPath);
                    folder = new File(commitPath);
                    listOfFiles = folder.listFiles();
                    if(listOfFiles != null)  {
                        logger.info("Found "+listOfFiles.length+" in "+commitPath);
                        for (File file : listOfFiles) {
                            if (file.isFile()) {
                                try {
                                    logger.info("Processing file "+file.getName());
                                    String stringObject = new String(fileManager.download(commitPath, file.getName()));
                                    ServiceCommitData commitData = (ServiceCommitData) GeneralUtils.fromText(stringObject);
                                    logger.info(commitData.toString());
                                    raterEJB.commitReservation(commitData.getReservationId(), commitData);
                                    if(!file.delete()) logger.info(file.getName()+" file deletion failed");
                                } catch(Exception e) {
                                    logger.error("Exception trying to commit pending reservations for "+serviceFamily+"'s "+agi,e);
                                }
                            }
                        }
                    } else {
                        logger.info("Files not found in "+commitPath);
                    }
                    cancelPath = persistencePath + pathSeparator + serviceFamily + pathSeparator + agi + pathSeparator + cancelFolder + pathSeparator;
                    logger.info("Checking cancel files in "+cancelPath);
                    folder = new File(cancelPath);
                    listOfFiles = folder.listFiles();
                    if(listOfFiles != null) {
                        logger.info("Found "+listOfFiles.length+" in "+cancelPath);
                        for (File file : listOfFiles) {
                            if (file.isFile()) {
                                try {
                                    logger.info("Processing file "+file.getName());
                                    String stringObject = new String(fileManager.download(commitPath, file.getName()));
                                    ServiceCancelData cancelData = (ServiceCancelData) GeneralUtils.fromText(stringObject);
                                    logger.info(cancelData.toString());
                                    raterEJB.cancelReservation(cancelData.getReservationId(), cancelData);
                                    if(!file.delete()) logger.info(file.getName()+" file deletion failed");
                                } catch(Exception e) {
                                    logger.error("Exception trying to cancel pending reservations for "+serviceFamily+"'s "+agi,e);
                                }
                            }
                        }
                    } else {
                        logger.info("Files not found in "+cancelPath);
                    }
                }
            }

        }

	public static void main(String[] args) throws RemoteException {

            InteraxTelephonyAgiServer startAgiServer = new InteraxTelephonyAgiServer();
            try {
                startAgiServer.logger.info("Starting AGI Server.");
                agiServerConfig = InteraxTelephonyAgiServerConfigLoader.getConfig();
                startAgiServer.logger.info("Initiating with:");
                startAgiServer.logger.info(agiServerConfig.AGI_SERVER_POOL_SIZE + " agi pool size.");
                startAgiServer.logger.info(agiServerConfig.AGI_SERVER_PORT + " port.");
                DefaultAgiServer defaultAgiServer = new DefaultAgiServer();
                defaultAgiServer.setPort(agiServerConfig.AGI_SERVER_PORT);
                ResourceBundleMappingStrategy resourceBundleMappingStrategy = new ResourceBundleMappingStrategy();
                resourceBundleMappingStrategy.setShareInstances(false);
                defaultAgiServer.setMappingStrategy(resourceBundleMappingStrategy);
                defaultAgiServer.setPoolSize(agiServerConfig.AGI_SERVER_POOL_SIZE);
                startAgiServer.logger.info("Process pending reservations before AGI Server startup");
                startAgiServer.processPendingReservations();
                startAgiServer.logger.info("AGI Server configured, about to start.");
                defaultAgiServer.startup();
                defaultAgiServer.shutdown();
            } catch (IllegalStateException e) {
                startAgiServer.logger.error("An error ocurred starting AGI server.",e);
            } catch (IOException e) {
                startAgiServer.logger.error("An error ocurred starting AGI server.",e);
            }

	}

}
