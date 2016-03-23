import glob
import time
import os
import hashlib
import commands

folders = [x[0] for x in os.walk("./src")]
srcFolderPath = folders[len(folders) - 1][2:]

folders = [x[0] for x in os.walk("./test")]
testFolderPath = folders[len(folders) - 1][2:]

#topFolder = folders[1].replace("./test/", "")
topFolder = ""

SrcFiles = []

def updateSrcFiles():
    AllFiles = glob.glob(srcFolderPath + "/*.java")
    global SrcFiles
    SrcFiles = []
    for f in AllFiles:
        file = open(f, "r")
        data = file.read()
        file.close()
        SrcFiles.append([f, hashlib.md5(data).hexdigest()])

def hasUpdateOrrcord():
    AllFiles = glob.glob(srcFolderPath + "/*.java")
    if len(AllFiles) != len(SrcFiles):
        return True
    for x in range(len(AllFiles)):
        file = open(AllFiles[x], "r")
        data = file.read()
        file.close()
        if SrcFiles[x][1] != hashlib.md5(data).hexdigest():
            return True
    return False

def isTestInFile(f):
    file = open(f, "r")
    data = file.read()
    file.close()
    out = False
    for line in data.split("\n"):
        if "/*" in line:
            out = True
        if "*/" in line:
            out = False
        if "@Test" in line and not out:
            lineParts = line.split("@Test")
            if "//" in lineParts[0] or "/*" in lineParts[0]:
                continue
            return True
    return False

def getAllTestFiles():
    files = []
    for f in glob.glob(testFolderPath + "/*.java"):
        if isTestInFile(f):
            files.append(f)
    return files

def getTestCommand():
    command = "java -cp test:bin:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore"
    for file in getAllTestFiles():
        command += " " + file.replace(".java", "").replace("test/", "").replace("/", ".")
    return command

def printErrorLine(e):
    nnext = False
    for line in e.split("\n"):
        if nnext:
            print line
            nnext = False
            continue
        if "1)" == line[:2]:
            nnext = True
            continue
        if "at " + topFolder in line:
            print line
            break

updateSrcFiles()

while 1:
    time.sleep(1)
    if hasUpdateOrrcord():
        os.system('clear')
        print "UPDATE:"
        updateSrcFiles()
        status, output = commands.getstatusoutput("javac -d bin/ -cp src " + srcFolderPath + "/*.java")
        if status != 0:
            print "COMPILE ERROR IN YOUR CODE"
            print output
            continue
        status, output = commands.getstatusoutput("javac -cp junit-4.12.jar:src -d bin/ " + testFolderPath + "/*.java")
        if status != 0:
            print "COMPILE ERROR IN TEST CASES"
            print output
            continue
        print "COMPILE SUCCES"
        status, output = commands.getstatusoutput(getTestCommand())
        outputLines = output.split("\n")
        outputStatus = outputLines[len(outputLines) - 2]
        print outputStatus
        if status != 0:
            print output