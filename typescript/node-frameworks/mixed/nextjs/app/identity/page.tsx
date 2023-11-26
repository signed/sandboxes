'use client';
import {validateIdentityToken} from './actions'
import React, {useEffect} from "react";

const uuid = globalThis.crypto.randomUUID();

// https://nextjs.org/docs/app/building-your-application/data-fetching/forms-and-mutations
// https://nextjs.org/docs/app/api-reference/functions/server-actions
export default function Identity() {
    const form = React.useRef<HTMLFormElement>(null);
    const boundAction = validateIdentityToken.bind(null, 'static');

    useEffect(() => {
        if (form.current === null) {
            return
        }
        form.current.submit()
    }, []);

    return (
        <>
            <h1>Marker: Identity</h1>
            {/*https://nextjs.org/docs/messages/react-hydration-error#solution-3-using-suppresshydrationwarning*/}
            <p suppressHydrationWarning>{uuid}</p>
            <form ref={form} action={boundAction}>
                <input name='uuid' defaultValue={uuid}/>
                <input name='some' defaultValue='thing'/>
                <button type="submit">Validate Identity Token</button>
            </form>
        </>
    )
}
