namespace java com.collectivecycling.fit2thrift.thrift.fit

struct FileId {
	1: i64 serialNumber;
 	2: i64 timeCreated;
 	3: i32 manufacturer;
 	4: i32 product;
 	5: i16 type
}
	
struct FileCreator {
	1: required i32 softwareVerion
}

struct Event {
	1: required i64 timestamp;
    2: i64 data;
    3: i16 event;
    4: i16 eventType;
    5: i16 eventGroup
}

struct TimestampCorrelation {
	1: required i64 timestamp;
    2: i64 fractionalTimestamp;
    3: i64 systemTimestamp;
    4: i64 fractionalSystemTimestamp;
    5: i64 localTimestamp;
    6: i64 timestampMs;
    7: i64 systemTimestampMs;
}

struct UserProfile {
    1: i16 gender;
    2: i16 age;
    3: double weight;
    4: string friendlyName;
}

struct DeviceInfo {
	1: required i64 timestamp;
    2: i64 serialNumber;
    3: i32 manufacturer;
    4: i32 product;
    5: double softwareVersion;
    6: i16 deviceIndex;
    7: i16 deviceType;
    8: i16 antDeviceType;
    9: i64 cumOperatingTime;
    10: double batteryVoltage;
    11: i16 batteryStatus;
    12: i16 sourceType;
    13: i16 hardwareVersion;
    14: i16 antNetwork;
}

// this isnt in the activity .Fit
struct DeviceSettings {
	1: required i64 timestamp;

}

struct Lap {
	1: required i64 timestamp;
    2: i64 startTime;
    3: i32 startPositionLat;
    4: i32 startPositionLong;
    5: i32 endPositionLat;
    6: i32 endPositionLong;
    7: double totalElapsedTime;
    8: double totalTimerTime;
    9: double totalDistance;
    10: i64 totalCycles;
    11: i64 totalWork;
    12: i32 messageIndex;
    13: i32 totalCalories;
    14: i32 totalFatCalories;
    15: double avgSpeed;
    16: double maxSpeed;
    17: i32 avgPower;
    18: i32 maxPower;
    19: i32 totalAscent;
    20: i32 totalDescent;
    21: i32 normalizedPower;
    22: i16 event;
    23: i16 eventType;
    24: i16 avgHeartRate;
    25: i16 maxHeartRate;
    26: i16 avgCadence;
    27: i16 maxCadence;
    28: i16 lapTrigger;
    29: i16 sport;
    30: i16 intensity;
    31: double enhancedAvgSpeed;
    32: double enhancedMaxSpeed;
    33: double avgFractionalCadence;
    34: double maxFractionalCadence;
    35: i16 subSport;
    36: double timeInHrZone;
  
}

struct Session {
	1: required i64 timestamp;
    2: i64 startTime;
    3: i32 startPositionLat;
    4: i32 startPositionLong;
    5: double totalElapsedTime;
    6: double totalTimerTime;
    7: double totalDistance;
    8: i64 totalCycles;
    9: i32 necLat;
    10: i32 necLong;
    11: i32 swcLat;
    12: i32 swcLong;
    13: i64 totalWork;
    14: i32 messageIndex;
    15: i32 totalCalories;
    16: i32 totalFatCalories;
    17: double avgSpeed;
    18: double maxSpeed;
    19: i32 avgPower;
    20: i32 maxPower;
    21: i32 totalAscent;
    22: i32 totalDescent;
    23: i32 firstLapIndex;
    24: i32 numLaps;
    25: i32 normalizedPower;
    26: double trainingStressScore;
    27: double intensityFactor;
    28: i16 event;
    29: i16 eventType;
    30: i16 sport;
    31: i16 subSport;
    32: i16 avgHeartRate;
    33: i16 maxHeartRate;
    34: i16 avgCadence;
    35: i16 maxCadence;
    36: i16 trigger;
    37: double enhancedAvgSpeed;
    38: double enhancedMaxSpeed;
    39: double avgFractionalCadence;
    40: double maxFractionalCadence;
    41: double timeInHrZone;
    
}

struct Activity {
	1: required i64 timestamp;
    2: double totalTimerTime;
    3: i32 numSessions;
    4: i16 type;
    5: i16 event;
    6: i16 eventType;
    7: i64 localTimestamp;
}

struct Record {
	1: required i64 timestamp;
    2: i32 positionLat;
    3: i32 positionLong;
    4: double distance;
    5: double altitude;
    6: double speed;
    7: i16 heartRate;
    8: i16 cadence;
    9: i32 power;
    10: byte temperature;
    11: double enhancedAltitude;
    12: double enhancedSpeed;
    13: double fractionalCadence;
}

struct FitData {
	1: FileId fileId;
	2: FileCreator fileCreator;
	3: list<Event> events;
	4: list<DeviceInfo> deviceInfo;
	5: list<Lap> laps;
	6: list<Session> sessions;
	7: list<Activity> activities;
	8: list<Record> records;
	9: list<DeviceSettings> deviceSettings;
	10: UserProfile userProfile;
	11: TimestampCorrelation timestampCorrelation;
}