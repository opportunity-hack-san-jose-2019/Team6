import * as functions from 'firebase-functions'
import * as admin from 'firebase-functions'
admin.initializeApp()

export const helpRequest = functions.https.onRequest((request,response)=>{
    admin.firestore().doc('help_request/KaMnonIYT1IAETctylBe').then(data=>{
        response.send("hello Can you see me!")
    })
})

export const volunteerList = functions.https.onRequest((request,response)=>{
    admin.firebase().doc().get()
    .then(data=>{
        
    })
})

export const 