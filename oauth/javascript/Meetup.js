const request = require('request');
const moment = require('moment-timezone');


function MeetupClient(meetupGroup) {

    const createEvent = (bearerToken, callback) => {
        const now = moment();
        const dayOfTheEvent = now.clone().add(5, 'days');

        console.log(dayOfTheEvent);
        const formDataForCreateEvent = {
            name: 'Draft created at ' + now.format(),
            publish_status: 'draft',
            time: dayOfTheEvent.valueOf(),
            description: 'This is just a placeholder',
            venue_id: 25894242,
            self_rsvp: true,
        };
        request.post('https://api.meetup.com/' + meetupGroup + '/events', {form: formDataForCreateEvent}, callback).auth(null, null, true, bearerToken);
    };

    return {
        createEvent
    }
}

module.exports = {
    MeetupClient
};