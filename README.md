# Fit2Thrift

Installation
------------

To use this package you will need to download and install into your local maven repository the Ant+ FIT SDK.  It can be found here https://www.thisisant.com/resources/fit.

Its been developed and tested using JDK 8.

After accepting the conditions, downloading and unzipping it, install it in your local maven repository with: 

    mvn install:install-file -Dfile=Downloads/FitSDKRelease_20.00.00/java/fit.jar -DgroupId=com.garmin -DartifactId=fit -Dversion=20.0.0 -Dpackaging=jar

Note you may need to revise the version number, if the FIT SDK has changed from 20.0.0.

Clone the project locally
    
    mkdir <<fit_play>>
    cd <<fit_play>>
    git clone https://github.com/CollectiveCycling/Fit2Thrift.git
    
If local repository FIT SDK version number is different. You'll need to update the fit.version tag in the properties section of the pom.xml.

Run the maven test script. It will generate the thrift sources and execute junit tests to do a test conversion. By default all debug statements are on and the output is fairly verbose.

    cd Fit2Thrift
    mvn test
    
To run on the command line
    
    mvn package
    java -jar target/fit2thrift-<<version>>-jar-with-dependencies.jar <<fit filename>>
    
Developing against
------------------

    mvn install

  
```java
import com.collectivecycling.fit2thrift.Fit;
import com.collectivecycling.fit2thrift.thrift.fit.FitData;
    
String fitDataFileName = ;
FitData fitData = Fit.toThrift(fitDataFileName);
```

fitData contains generated thrift APIs to access Activites, Laps, Sessions etc that corresponds to the following 

```
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
```