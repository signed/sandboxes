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

const meetupClient = new MeetupClient('Softwerkskammer-Karlsruhe');
meetupClient.createEvent('<your token here', (error, response, body) => {
    if (error) {
        console.log('an error occurred: \n' + error);
        return;
    }

    console.log("status code: " + response.statusCode);
    const info = JSON.parse(body);
    console.log(info);
});