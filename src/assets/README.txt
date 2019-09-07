############################################################
# +------------------------------------------------------+ #
# |                 Uni Adventures Notes                 | #
# +------------------------------------------------------+ #
############################################################

# Version 0.3

############################################################
# +------------------------------------------------------+ #
# |                  Game Runtime Notes                  | #
# +------------------------------------------------------+ #
############################################################

# DO NOT DELETE assets/maps/emptyMap.map, YOU WILL BREAK THE GAME

# When using the map editor be sure that you have saved the
# map before loading in a new one or exiting the game,
# otherwise YOU WILL LOOSE IT.

# When using the map editor setting your brush to a tile id
# of 0 will make it an eraser

############################################################
# +------------------------------------------------------+ #
# |                    Command Usage                     | #
# +------------------------------------------------------+ #
############################################################

# Uni Adventures contains a shell, which can be used to type
# various commands. Below are a list of commands, how to use
# them, and what they do. The shell automatically closes
# after entering each command.
#
# To open the shell press the forward slash key "/"
# To close the shell press the escape key "Esc"

> help
> help 2

# The help command will show you a list of commands in game.
# You will have to open the shell again after entering this
# command to see the list, as it is displayed in the shell's
# output window.

> tp <x> <y>

# The tp (also known as teleport) command will move the
# player to the x and y coordinates entered.

> editor

# The editor command will toggle the ability for you to use
# map editing commands such as saving, loading, and listing
# maps.

> saveMap <map name>

# The saveMap command will save the currently loaded map
# that you can see rendered to the assets/maps directory
# with the name you entered. Its name will also be added
# to the list of maps.

> loadMap <map name>

# The loadMap command will load a map from the assets/maps
# directory and replace the currently rendered map.

> setBrush <tile id>

# The setBrush command will assign your mouse pointer with a
# tile id from the main tile sheet. Anywhere you click on 
# the map, while in editor mode, it will replace that tile 
# with the tile of your brush.

> grid

# The grid command will toggle a tile grid which will assist
# in the placement of tiles.

> listMaps

# The listMaps command will list the maps from assets/maps.cfg.
# This file contains all of the map names and they will all be
# printed in the console because of space issues with the shell.
# 10 lines is the limit of the shells output capacity, if there
# are more than 10 maps you would not be able to see them.

> createMap <map name> <x> <y> <num of layers>

# The createMap command will create a new map in the assets/maps/
# directory. the x and y params are the number of tiles you want
# in the map. 40 by 23 being the smallest to allow the camera to
# snap nicley with the map

> setLayer <layer number>

# The setLayer command will allow you to switch between the 
# different drawing layers of the map. For example if a map
# has been created with 3 layers, they would be layers 0, 1, 2

> toggleDebugMode

# The toggleDebugMode command will toggle a debug interface which
# will show you various information and attributes about the game
# and its objects, such as the version, FPS, object coordinates etc.

> collision

# The collision command allows you to enter collision map mode where
# you can create and delete collision boxes by clicking 2 opposite points
# anywhere on the map.

> toggleSnap

# The toggleSnap command will toggle the collision boxes being created
# relative to the tile grid to allow you to create absolute collision
# boxes if you need to for other objects that don't take up the entire
# tile space.

> saveCol <map name>

# The saveCol command will save the collision boxes to the assets/collision
# directory. Be aware the collision map name MUST be the same as the normal
# map name (the map made with tiles)

> loadCol <map name>

# The loadCol command will load a collision map from the assets/collision
# directory and replace the currently rendered collision map.
