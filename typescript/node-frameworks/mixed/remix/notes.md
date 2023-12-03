- perform any mutation in an `action` and not a `loader`.
  Otherwise, you open your users to [Cross-Site Request Forgery](https://developer.mozilla.org/en-US/docs/Glossary/CSRF) attacks.
  See https://remix.run/docs/en/main/utils/sessions#session-gotchas
