# SmartMap (last Update 3.02.2019: Jonas Witt)

Assignment VI: Implementing a GPS-visualisation for smart homes based on user maps.

A MATLAB connector provides a continuous file stream of GPS-coordinates which are saved in the SensorData folder (lib).
The Class SmartMap provides interfaces in order to visualise coordinates as part of the .svg based user map which were retrieved from the SensorData folder priorly.
It makes use of the Class CreateMap to query user inputs and calibrate mappings. 