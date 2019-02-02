clear
delete ./SensorData/LAT.txt
delete ./SensorData/LON.txt
delete ./SensorData/ALT.txt
delete ./SensorData/SPEED.txt

connector on connect;
mobile = mobiledev;

m.PositionSensorEnabled = 1;
m.Logging = 1;
%m.Logging = 0;

while 1 == 1
    [lat, lon, t, speed, course, alt, horizacc] = poslog(mobile);
    %LAT = table(lat);
    %LON = table(lon);
    
    dlmwrite('./SensorData/LAT.txt', lat, '-append');
    dlmwrite('./SensorData/LON.txt', lon, '-append');
    dlmwrite('./SensorData/ALT.txt', alt, '-append');
    dlmwrite('./SensorData/SPEED.txt', speed, '-append');
    discardlogs(mobile);
    pause(1);
end