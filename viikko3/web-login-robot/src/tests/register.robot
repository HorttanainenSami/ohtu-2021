*** Settings ***
Resource  resource.robot
Resource  login_resource.robot

Suite Setup  Open And Configure Browser
Suite Teardown  Close Browser
Test Setup  Go To Register Page

*** Test Cases ***
Register With Valid Username And Password
    Set Username  sam
    Set Password  salasana123
    Set Password Confirmation  salasana123
    Submit Registration
    Welcome Page Should Be Open

Register With Too Short Username And Valid Password
    Set Username  sa
    Set Password  salasana123
    Set Password Confirmation  salasana123
    Submit Registration
    Register Should Fail With Message  Username must be atleast 3 characters long and contain only alphabet

Register With Valid Username And Too Short Password
    Set Username  sami
    Set Password  sal
    Set Password Confirmation  salasana123
    Submit Registration
    Register Should Fail With Message  Password must be atleast 8 characters long

Register With Nonmatching Password And Password Confirmation
    Set Username  sami
    Set Password  salasana123
    Set Password Confirmation  salasana12
    Submit Registration
    Register Should Fail With Message  Password and password confirmation doesn't match

Login After Successful Registration
    Set Username  sami
    Set Password  salasana123
    Set Password Confirmation  salasana123
    Click Button  Register
    Go To Login Page
    Set Username  sami
    Set Password  salasana123
    Submit Credentials
    Login Should Succeed

Login After Failed Registration
    Set Username  sama123
    Set Password  salasana3
    Set Password Confirmation  salasana123
    Click Button  Register
    Go To Login Page
    Set Username  sama123
    Set Password  salasana3
    Submit Credentials
    Login Should Fail With Message  Invalid username or password 
