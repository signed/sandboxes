import {Octokit} from 'octokit'

type GithubCoordinates = { owner: string, repo: string }
type Framework = { name: string, github: GithubCoordinates }

export const github = (name: string, url: string): Framework => {
  const parts = url.split('/');

  const owner = parts[3] ?? 'bogus'
  const repo = parts[4] ?? 'bogus'
  return {
    name,
    github: {
      owner,
      repo
    }

  }
}

const frameworks: Framework [] = [
  github('nest.js', 'https://github.com/nestjs/nest'),
  github('koa', 'https://github.com/koajs/koa'),
  github('tinyhttp', 'https://github.com/tinyhttp/tinyhttp'),
  github('express', 'https://github.com/expressjs/express'),
  github('fastify', 'https://github.com/fastify/fastify'),
  github('restify', 'https://github.com/restify/node-restify'),
  github('next.js', 'https://github.com/vercel/next.js'),
  github('remix', 'https://github.com/remix-run/remix'),
  github('hapi', 'https://github.com/hapijs/hapi'),
]

const octokit = new Octokit();

const stats = await Promise.all(frameworks.map(async framework => {
  const response = await octokit.rest.repos.get({
    ...framework.github
  });
  const data = response.data;

  const stars = data.stargazers_count;
  const language = data.language;
  const license = data.license?.name;

  return {name: framework.name, stars, language, license}
}));

console.table(stats)
