*** Settings ***
Resource  resource.robot


*** Test Cases ***
Register With Already Taken Username And Valid Password
    Create User And Input Register Command
    Input Credentials  sami  sami1234
    Output Should Contain  User with username sami already exists

Register With Too Short Username And Valid Password
    Start Application And Input Register Command
    Input Credentials  ss  asdasdas
    Output Should Contain  Username must be atleast 3 characters long

Register With Valid Username And Too Short Password
    Start Application And Input Register Command
    Input Credentials  sss  asdas3d
    Output Should Contain  Password must be atleast 8 characters long

Register With Valid Username And Long Enough Password Containing Only Letters
    Start Application And Input Register Command
    Input Credentials  sss  asdasasdasdasdad
    Output Should Contain  Password must contain numbers or special characters
*** Keywords ***
Create User And Input Register Command
    Create User  sami  sami1234
    Input Register Command

Start Application And Input Register Command
    Run Application
    Input Register Command
