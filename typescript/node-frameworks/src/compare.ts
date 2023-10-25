import {Octokit, App} from 'octokit'

const octokit = new Octokit();
await octokit.rest.repos.get()
