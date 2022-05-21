export {};
const messages = ['hi', 'hello', 'hey', 'how are you?', 'how can i help you?', 'sure', 'good', 'thanks', 'exit'];

function getRandomMessage() {
    const i = Math.floor(Math.random() * messages.length);
    return messages[i];
}

const connection = Deno.listenDatagram({ port: 10000, transport: 'udp', hostname: '127.0.0.1' });
for await(const datagram of connection) {
    const [payload, senderAddress] = datagram;
    const receivedMessage = new TextDecoder().decode(payload);
    console.log(`>remote< ${receivedMessage}`)
    if (receivedMessage === 'exit') {
        Deno.exit(1);
    }
    const response = getRandomMessage();
    console.log(`>me< ${response}`);
    await connection.send(new TextEncoder().encode(response), senderAddress);
    if (response === 'exit') {
        Deno.exit(1);
    }
}
