# LoginAppInnovaccer
This is the app for entry management software for Innovaccer Intern assignment. 
This is a very minimilistic app but fully functional implementing all functionalities asked in the asssignment.
On startup, a form is displayed on the screen which has to be filled by both host and visitor.User can choose between host and visitor using
a dropdown menu. Visitor won't be able to login unlesss there is a host logged in before him. As soon as host logs in, visitor can login easily.
If there is already a host logged in and another host logs in then previous one will be replaced by the new one.
As soon as visitor logs in host receives a text message and an email with the details of the visitor.Visitor is then taken to another
page with just a single check out button. When visitor clicks on that button the details of the visit are being mailed to the visitor.
For database Firebase has been used. For sending mail mail API has been used and for sending messsages Android inbuilt sms Manager is used.
