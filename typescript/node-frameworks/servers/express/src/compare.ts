import { Octokit } from 'octokit'

export type GithubCoordinates = { owner: string; repo: string }
export type Framework = { name: string; github: GithubCoordinates }

export const github = (name: string, url: string): Framework => {
  const parts = url.split('/')

  const owner = parts[3] ?? 'bogus'
  const repo = parts[4] ?? 'bogus'
  return {
    name,
    github: {
      owner,
      repo,
    },
  }
}

export const compare = async (frameworks: Framework[]) => {
  const octokit = new Octokit()
  const stats = await Promise.all(
    frameworks.map(async (framework) => {
      const response = await octokit.rest.repos.get({
        ...framework.github,
      })
      const data = response.data

      const stars = data.stargazers_count
      const language = data.language
      const license = data.license?.name

      return { name: framework.name, stars, language, license }
    }),
  )

  console.table(stats)
}
