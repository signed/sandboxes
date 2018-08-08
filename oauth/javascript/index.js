const express = require('express');
const passport = require('passport');
const fs = require("fs");
const MeetupStrategy = require('passport-meetup-oauth2').Strategy;
const conf = JSON.parse(fs.readFileSync("conf.json"));
const {MeetupClient} = require('./Meetup');

// Passport session setup.
//   To support persistent login sessions, Passport needs to be able to
//   serialize users into and deserialize users out of the session.  Typically,
//   this will be as simple as storing the user ID when serializing, and finding
//   the user by ID when deserializing.  However, since this example does not
//   have a database of user records, the complete Meetup profile is
//   serialized and deserialized.
passport.serializeUser(function (user, done) {
    done(null, user);
});

passport.deserializeUser(function (obj, done) {
    done(null, obj);
});


const options = {
    clientID: conf.clientID,
    clientSecret: conf.clientSecret,
    callbackURL: "http://localhost:3000/auth/meetup/callback",
    scope: "event_management"
};
passport.use(new MeetupStrategy(options, function (accessToken, refreshToken, profile, done) {
    // asynchronous verification, for effect...

    console.log(accessToken);
    console.log(refreshToken);
    console.log(profile);

    process.nextTick(function () {
        // To keep the example simple, the user's Meetup profile is returned to
        // represent the logged-in user.  In a typical application, you would want
        // to associate the Meetup account with a user record in your database,
        // and return that user instead.
        const tokens = {accessToken, refreshToken};
        return done(null, tokens);
    });

}));


const app = express();

app.use(require('cookie-parser')());
app.use(require('body-parser').urlencoded({extended: true}));
app.use(require('express-session')({
    secret: 'keyboard cat',
    resave: true,
    saveUninitialized: true
}));
app.use(passport.initialize({userProperty: 'user'}));
app.use(passport.session());


app.get('/', (req, res) => {
    if (req.user && req.user.accessToken) {
        res.send('<form action="/event" method="post">\n' +
            '  <input type="submit" value="Create Event">\n' +
            '</form>')
    } else {
        res.send('<a href="/auth/meetup">Log In</a>');
    }
});

app.post('/event', (req, res) => {
    const meetupClient = new MeetupClient('Softwerkskammer-Karlsruhe');
    meetupClient.createEvent(req.user.accessToken, (error, response, body) => {
        if (error) {
            res.send(error);
            return;
        }
        const info = JSON.parse(body);
        res.send(info);
    });

});

app.get('/auth/meetup', passport.authenticate('meetup'));

app.get('/auth/meetup/callback',
    passport.authenticate('meetup', {failureRedirect: '/login'}),
    function (req, res) {
        // Successul authentication, redirect home.
        res.redirect('/');
    });
app.listen(3000, () => console.log('Example app listening on port 3000!'));