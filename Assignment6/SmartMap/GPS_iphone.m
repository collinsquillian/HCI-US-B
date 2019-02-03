clear
delete ./SmartMap/lib/SensorData/LAT.txt
delete ./SmartMap/lib/SensorData/LON.txt
delete ./SmartMap/lib/SensorData/ALT.txt
delete ./SmartMap/lib/SensorData/SPEED.txt

%connector on connect;
mobile = mobiledev;

m.PositionSensorEnabled = 1;
m.Logging = 1;
%m.Logging = 0;

while 1 == 1
    [lat, lon, t, speed, course, alt, horizacc] = poslog(mobile);
    %LAT = table(lat);
    %LON = table(lon);
    
    dlmwrite('./SmartMap/lib/SensorData/LAT.txt', lat, '-append');
    dlmwrite('./SmartMap/lib/SensorData/LON.txt', lon, '-append');
    dlmwrite('./SmartMap/lib/SensorData/ALT.txt', alt, '-append');
    dlmwrite('./SmartMap/lib/SensorData/SPEED.txt', speed, '-append');
    discardlogs(mobile);
    pause(1);
end