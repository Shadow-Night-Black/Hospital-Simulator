# Hospital Simulator Coursework

To run any test harness you will need to add the following import statements to your code:
```
import hospital.*;
import people.*;
import illness.*;
```
To compile my code compile the Main.java class with
```
java hospital/Main.java
```
To run my code use the following command
```
java hospital.Main <configFile.cfg>
```
where <configFile.cfg> is a file confirming to the standard in the spec, namely:

the first line starts with
```
hospital:<bedNumber>,<theatreNumber>
```
and with subsequent lines being any of the following:
```
patient:<gender>,<age>,<illnessId>,<daysUntilRecovered>
doctor:<gender>,<age>
surgeon:<gender>,<age>
limbSurgeon:<gender>,<age>
organSurgeon:<gender>,<age>
```

and where <gender> is M or F (male or female respectively)
<illnessId> is an id for an illness that can be found in the Illness folder (more on that later)
<daysUntilRecovered> is a number specifing the days until they recover, note that if they are given an illness (any id except 0)
	this field will be overwritten when they are treated with the illness recovery time

the config found in the spec can be found in the BasicSim.cfg file
(note that the file extension does not matter, but .cfg is prefered)

To run my code with the supplied config file run
```
java hospital.Main BasicSim.cfg
```


For my extension I put all of the infomation about Illness into xml files found in the folder Illnesses
my program will read in any file found in that folder and attempt to parse it as an xml file containing infomation about a new Illnesses
There are some limitations:

There can only be one Illness per Illness id (it will throw an error if it find more than one)
There can only be one Illness per file

an Example (Java Flu) is found below

<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd">
<properties>
<entry key="minRecoveryTime">3</entry>
<entry key="name">Java Flu</entry>
<entry key="maxRecoveryTime">3</entry>
<entry key="requiresTheatre">false</entry>
<entry key="id">2</entry>
</properties>

There must be entry keys for the following fields:
name (string)
id (int)
minRecoveryTime (int)
maxRecoveryTime (int)
requiresTheatre (boolean)


The program will generate patients with any Illness found in this folder
Unfortuantley, what each Doctor and treat is hard coded, (although modifable at runtime) so any new illness will need either:
a) an existing Doctor's/Surgeon canTreat/canOperate hashmap updated with the new Illness id
b) a new Doctor/Surgeon (extending the orignal Doctor/Surgeon) whose canTreat/canOperate inludes the new Illness ID
This new Doctor will also require a new section in the readConfig method in the Main class to be able to be created

If one of these methods are not implement for a new Illness, the patient will never be treated as no Doctor is able to treat the new Illness


