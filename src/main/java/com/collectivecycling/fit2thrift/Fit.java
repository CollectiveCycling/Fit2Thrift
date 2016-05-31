package com.collectivecycling.fit2thrift;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.collectivecycling.fit2thrift.thrift.fit.Activity;
import com.collectivecycling.fit2thrift.thrift.fit.DeviceInfo;
import com.collectivecycling.fit2thrift.thrift.fit.DeviceSettings;
import com.collectivecycling.fit2thrift.thrift.fit.Event;
import com.collectivecycling.fit2thrift.thrift.fit.FileCreator;
import com.collectivecycling.fit2thrift.thrift.fit.FileId;
import com.collectivecycling.fit2thrift.thrift.fit.FitData;
import com.collectivecycling.fit2thrift.thrift.fit.Lap;
import com.collectivecycling.fit2thrift.thrift.fit.Record;
import com.collectivecycling.fit2thrift.thrift.fit.Session;
import com.collectivecycling.fit2thrift.thrift.fit.TimestampCorrelation;
import com.collectivecycling.fit2thrift.thrift.fit.UserProfile;
import com.garmin.fit.ActivityMesg;
import com.garmin.fit.ActivityMesgListener;
import com.garmin.fit.CapabilitiesMesgListener;
import com.garmin.fit.Decode;
import com.garmin.fit.DeviceInfoMesg;
import com.garmin.fit.DeviceInfoMesgListener;
import com.garmin.fit.DeviceSettingsMesg;
import com.garmin.fit.DeviceSettingsMesgListener;
import com.garmin.fit.EventMesg;
import com.garmin.fit.EventMesgListener;
import com.garmin.fit.Field;
import com.garmin.fit.FileCreatorMesg;
import com.garmin.fit.FileCreatorMesgListener;
import com.garmin.fit.FileIdMesg;
import com.garmin.fit.FileIdMesgListener;
import com.garmin.fit.FitRuntimeException;
import com.garmin.fit.LapMesg;
import com.garmin.fit.LapMesgListener;
import com.garmin.fit.MesgBroadcaster;
import com.garmin.fit.MesgCapabilitiesMesg;
import com.garmin.fit.MesgCapabilitiesMesgListener;
import com.garmin.fit.RecordMesg;
import com.garmin.fit.RecordMesgListener;
import com.garmin.fit.SessionMesg;
import com.garmin.fit.SessionMesgListener;
import com.garmin.fit.TimestampCorrelationMesg;
import com.garmin.fit.TimestampCorrelationMesgListener;
import com.garmin.fit.UserProfileMesg;
import com.garmin.fit.UserProfileMesgListener;
import com.google.common.base.CaseFormat;
import com.google.common.io.ByteSource;
import com.google.common.io.Files;

/**
 * Created by nick on 30/05/2016.
 */
public class Fit {
    private static final Logger log = LoggerFactory.getLogger(Fit.class);
    private static FitData fitData;

    private static class Listener implements FileIdMesgListener, FileCreatorMesgListener, ActivityMesgListener,
            LapMesgListener, RecordMesgListener, SessionMesgListener, EventMesgListener, DeviceInfoMesgListener,
            DeviceSettingsMesgListener, UserProfileMesgListener, TimestampCorrelationMesgListener, MesgCapabilitiesMesgListener {

        public void onMesg(DeviceInfoMesg mesg) {
            DeviceInfo deviceInfo = new DeviceInfo();

            for (Field f : mesg.getFields()) {
                if (f.getName().equals("unknown"))
                    continue;

                String camelName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, f.getName());
                log.trace (String.format("fit field: '%s' %s -> struct field '%s'.",f.getName(),f.getValue(),camelName));
                DeviceInfo._Fields field = DeviceInfo._Fields.findByName(camelName);
                log.trace("struct field: " + field);
                try {
                    deviceInfo.setFieldValue(field, f.getValue());
                } catch (Exception e) {
                    log.error("DeviceInfo f: " + camelName + " " + e);
                }
            }

            log.debug("deviceInfo: " + deviceInfo);
            fitData.addToDeviceInfo(deviceInfo);
        }

        public void onMesg(EventMesg mesg) {
            Event event = new Event();

            for (Field f : mesg.getFields()) {
                if (f.getName().equals("unknown"))
                    continue;

                String camelName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, f.getName());
                log.trace (String.format("fit field: '%s' %s -> struct field '%s'.",f.getName(),f.getValue(),camelName));
                Event._Fields field = Event._Fields.findByName(camelName);
                log.trace("struct field: " + field);
                try {
                    event.setFieldValue(field, f.getValue());
                } catch (Exception e) {
                    log.error("events f: " + camelName + " " + e);
                }
            }

            log.debug("event: " + event);
            fitData.addToEvents(event);
        }

        public void onMesg(SessionMesg mesg) {
            Session session = new Session();

            for (Field f : mesg.getFields()) {
                if (f.getName().equals("unknown"))
                    continue;

                String camelName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, f.getName());
                log.trace (String.format("fit field: '%s' %s -> struct field '%s'.",f.getName(),f.getValue(),camelName));
                Session._Fields field = Session._Fields.findByName(camelName);
                log.trace("struct field: " + field);
                try {
                    session.setFieldValue(field, f.getValue());
                } catch (Exception e) {
                    log.error("session f: " + camelName + " " + e);
                }
            }

            log.debug("session: " + session);
            fitData.addToSessions(session);
        }

        public void onMesg(RecordMesg mesg) {
            Record record = new Record();

            for (Field f : mesg.getFields()) {
                if (f.getName().equals("unknown"))
                    continue;

                String camelName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, f.getName());
                log.trace (String.format("fit field: '%s' %s -> struct field '%s'.",f.getName(),f.getValue(),camelName));
                Record._Fields field = Record._Fields.findByName(camelName);
                log.trace("struct field: " + field);
                try {
                    record.setFieldValue(field, f.getValue());
                } catch (Exception e) {
                    log.error("record f: " + camelName + " " + e);
                }
            }

            log.debug("record: " + record);
            fitData.addToRecords(record);
        }

        public void onMesg(LapMesg mesg) {
            Lap lap = new Lap();

            for (Field f : mesg.getFields()) {
                if (f.getName().equals("unknown"))
                    continue;

                String camelName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, f.getName());
                log.trace (String.format("fit field: '%s' %s -> struct field '%s'.",f.getName(),f.getValue(),camelName));
                Lap._Fields field = Lap._Fields.findByName(camelName);
                log.trace("struct field: " + field);
                try {
                    lap.setFieldValue(field, f.getValue());
                } catch (Exception e) {
                    log.error("lap f: " + camelName + " " + e);
                }
            }

            log.debug("lap: " + lap);
            fitData.addToLaps(lap);
        }

        public void onMesg(ActivityMesg mesg) {
            Activity activity = new Activity();

            for (Field f : mesg.getFields()) {
                if (f.getName().equals("unknown"))
                    continue;

                String camelName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, f.getName());
                log.trace (String.format("fit field: '%s' %s -> struct field '%s'.",f.getName(),f.getValue(),camelName));
                Activity._Fields field = Activity._Fields.findByName(camelName);
                log.trace("struct field: " + field);
                try {
                    activity.setFieldValue(field, f.getValue());
                } catch (Exception e) {
                    log.error("activity f: " + camelName + " " + e);
                }
            }

            log.debug("activity: " + activity);
            fitData.addToActivities(activity);
        }

        public void onMesg(FileCreatorMesg mesg) {
            FileCreator fileCreator = new FileCreator();

            // only one field used but keeping logic the
            for (Field f : mesg.getFields()) {
                if (f.getName().equals("unknown"))
                    continue;

                if (f.getName().equals("software_version")) {
                    fileCreator.setSoftwareVerion((Integer) f.getValue());
                }
            }

            log.debug("fileCreator: " + fileCreator);
            // only one FileCreator record to add
            fitData.setFileCreator(fileCreator);
        }

        public void onMesg(FileIdMesg mesg) {
            FileId fileId = new FileId();

            for (Field f : mesg.getFields()) {
                if (f.getName().equals("unknown"))
                    continue;

                String camelName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, f.getName());
                log.trace (String.format("fit field: '%s' %s -> struct field '%s'.",f.getName(),f.getValue(),camelName));
                FileId._Fields field = FileId._Fields.findByName(camelName);
                log.trace("struct field: " + field);
                try {
                    fileId.setFieldValue(field, f.getValue());
                } catch (Exception e) {
                    log.error("fileId f: " + camelName + " " + e);
                }

            }

            log.debug("fileId: " + fileId);
            // only one FileId record to add
            fitData.setFileId(fileId);
        }

        public void onMesg(DeviceSettingsMesg mesg) {
            DeviceSettings deviceSettings = new DeviceSettings();

            for (Field f : mesg.getFields()) {
                if (f.getName().equals("unknown"))
                    continue;

                String camelName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, f.getName());
                log.trace (String.format("fit field: '%s' %s -> struct field '%s'.",f.getName(),f.getValue(),camelName));
                DeviceSettings._Fields field = DeviceSettings._Fields.findByName(camelName);
                log.trace("struct field: " + field);
                try {
                    deviceSettings.setFieldValue(field, f.getValue());
                } catch (Exception e) {
                    log.error("fileId f: " + camelName + " " + e);
                }

            }

            log.debug("deviceSettings: " + deviceSettings);
            fitData.addToDeviceSettings(deviceSettings);

        }

        public void onMesg(UserProfileMesg mesg) {
            UserProfile userProfile = new UserProfile();


            for (Field f : mesg.getFields()) {
                if (f.getName().equals("unknown"))
                    continue;

                String camelName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, f.getName());
                log.trace (String.format("fit field: '%s' %s -> struct field '%s'.",f.getName(),f.getValue(),camelName));
                UserProfile._Fields field = UserProfile._Fields.findByName(camelName);
                log.trace("struct field: " + field);
                try {
                    userProfile.setFieldValue(field, f.getValue());
                } catch (Exception e) {
                    log.error("fileId f: " + camelName + " " + e);
                }

            }

            log.debug("userProfile: " + userProfile);
            fitData.setUserProfile(userProfile);

        }

        public void onMesg(TimestampCorrelationMesg mesg) {
            TimestampCorrelation timestampCorrelation = new TimestampCorrelation();

            for (Field f : mesg.getFields()) {
                if (f.getName().equals("unknown"))
                    continue;

                String camelName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, f.getName());
                log.trace (String.format("fit field: '%s' %s -> struct field '%s'.",f.getName(),f.getValue(),camelName));
                TimestampCorrelation._Fields field = TimestampCorrelation._Fields.findByName(camelName);
                log.trace("struct field: " + field);
                try {
                    timestampCorrelation.setFieldValue(field, f.getValue());
                } catch (Exception e) {
                    log.error("fileId f: " + camelName + " " + e);
                }

            }
            log.debug("timestampCorrelation: " + timestampCorrelation);
            fitData.setTimestampCorrelation(timestampCorrelation);

        }

        public void onMesg(MesgCapabilitiesMesg mesg) {

            String text = "MesgCapabilitiesMesg; ";
            for (Field f : mesg.getFields()) {
                text=text+" "+f.getName()+": "+f.getValue()+" ";
            }
            log.debug(text);
        }
    }

    public static FitData toThrift(byte[] fitDataFile) {
        fitData = new FitData();

        Decode decode = new Decode();

        MesgBroadcaster mesgBroadcaster = new MesgBroadcaster(decode);

        Listener listener = new Listener();

        mesgBroadcaster.addListener((FileIdMesgListener) listener);
        mesgBroadcaster.addListener((ActivityMesgListener) listener);
        mesgBroadcaster.addListener((SessionMesgListener) listener);
        mesgBroadcaster.addListener((LapMesgListener) listener);
        mesgBroadcaster.addListener((RecordMesgListener) listener);
        mesgBroadcaster.addListener((FileCreatorMesgListener) listener);
        mesgBroadcaster.addListener((EventMesgListener) listener);
        mesgBroadcaster.addListener((DeviceInfoMesgListener) listener);

        // added the following but they are not in an activity.fit file
        //mesgBroadcaster.addListener((DeviceSettingsMesgListener) listener);
        //mesgBroadcaster.addListener((UserProfileMesgListener) listener);
        //mesgBroadcaster.addListener((TimestampCorrelationMesgListener) listener);
        //mesgBroadcaster.addListener((MesgCapabilitiesMesgListener) listener);

        InputStream in;
        try {
            in = ByteSource.wrap(fitDataFile).openStream();
            mesgBroadcaster.run(in);
            try {
                in.close();
            } catch (java.io.IOException f) {
                throw new RuntimeException(f);
            }
        } catch (IOException e1) {
            log.error("IO Exception decoding file: " + e1.getMessage());
            throw new RuntimeException(e1);
        } catch (FitRuntimeException e) {
            log.error("Exception decoding file: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return fitData.deepCopy();
    }

    public static FitData toThrift(String fitDataFileName) {
        log.info("toThrift .fit file: " + fitDataFileName);
        FileInputStream in;
        try {
            in = new FileInputStream(fitDataFileName);
        } catch (java.io.IOException e) {
            throw new RuntimeException("Error opening file " + fitDataFileName);
        }

        File file = new File(fitDataFileName);
        byte[] fitDataFile;
        FitData fitData;
        try {
            fitDataFile = Files.toByteArray(file);
            fitData = Fit.toThrift(fitDataFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fitData;
    }

    public static void main(String[] args) {
        log.info("start ");

        if (args.length != 1) {
            System.out.println("Usage: java -jar fit2thrift.jar -cp Fit <filename>");
            return;
        }

        String fitDataFileName = args[0];
        FitData fitData = toThrift(fitDataFileName);

        log.info("finish - Decoded FIT file " + args[0] + " into thrift structs - fileId: " + fitData.getFileId() + ".");
    }
}
