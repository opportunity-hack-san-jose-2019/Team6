const functions = require('firebase-functions');

const admin = require('firebase-admin')

// const app = admin.initializeApp()
// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//


exports.helpRequest = functions.https.onRequest((request, response) => {
 response.send("Hello from Firebase!");

 console.log(response)
//  admin.firestore().doc('help_request/2MAAfEsTGzbAhzoDZ8DP').get().then(data=>{
//             response.send(data)
//             // console.log(data)
//         }).catch(error=>{
//             response.send(error)
//         })
});
