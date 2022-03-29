import nodemailer, { SendMailOptions } from 'nodemailer';


const theFunction = async () => {
  // create a temporary account at https://ethereal.email
  const user = '<create-one>@ethereal.email';
  const pass = '<your password>';

  const transporter = nodemailer.createTransport({
    host: 'smtp.ethereal.email',
    port: 587,
    logger: true,
    debug: true,
    auth: {
      user,
      pass
    }
  });
  const email: SendMailOptions = {
    from: 'Daemon <deamon@nodemailer.com>',
    to: user,
    subject: 'Hello You',
    text: 'Hello World'

  };
  const report = await transporter.sendMail(email);
  console.log(JSON.stringify(report, null, 2));
};
theFunction();
