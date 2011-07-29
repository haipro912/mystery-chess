#! /bin/bash

# Change to your proper path <the-path>/<project-name>/bin
cd ~/workspace/TRUNK/main2/mchess/bin
rmic mysterychess.network.RmiClient
rmic mysterychess.network.RmiServer
