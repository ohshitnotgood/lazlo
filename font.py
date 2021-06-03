import os

d = str(input())

currentDirectory = "C:\\fonts\\" + d
allFile = os.listdir(currentDirectory)

for eachItem in allFile:
    oldName = eachItem
    newName = oldName.lower()
    newName = newName.replace("-", "_")
    os.rename(currentDirectory + "\\" + oldName, currentDirectory + "\\" + newName)
    print("Renamed " + oldName + " to " + newName)