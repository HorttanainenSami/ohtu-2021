*** Settings ***
Resource  login_resource.robot
Resource  resource.robot
Suite Setup  Open And Configure Browser
Suite Teardown  Close Browser
Test Setup  Create User And Go To Login Page

*** Test Cases ***
Login With Correct Credentials
    Set Username  kalle
    Set Password  kalle123
    Submit Credentials
    Login Should Succeed

Login With Nonexistent Username
    Reset Application
    Set Username  nonexistent
    Set Password  wrong
    Submit Credentials
    Login Should Fail With Message  Invalid username or password

Login With Incorrect Password
    Set Username  kalle
    Set Password  kalle456
    Submit Credentials
    Login Should Fail With Message  Invalid username or password
