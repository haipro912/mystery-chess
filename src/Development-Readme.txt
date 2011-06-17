After building the source code, use rmic to build RMI stufs:
  # set path to <jdk dir>/bin e.g. set path=C:\Program Files\Java\jdk1.5.0_11\bin
  # cd <project build dir>
  # rmic mystery.network.RmiClient
  # rmic mystery.network.RmiServer

Alternatively you can correct the paths in the BuildRmi.bat file 
and use it to build the RMI Stuffs