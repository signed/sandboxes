import {redirect} from 'next/navigation';
import {cookies} from "next/headers";

// https://stackoverflow.com/questions/58173809/next-js-redirect-from-to-another-page
// https://stackoverflow.com/questions/76238405/how-im-get-cookies-on-a-server-side-component-in-next-js-13
export default async function Home() {
    //todo: check that identity is still valid
    if (!cookies().has("identity")) {
        redirect('/identity');
    }
    return <>
        <h1 className="text-3xl font-bold underline">Welcome</h1>
        <p>{cookies().get('identity')?.value}</p>
    </>
}

