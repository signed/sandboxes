import {fromHtml} from 'hast-util-from-html'
import * as cherio from 'cheerio'

const response = await fetch("https://www.arbeitsagentur.de/unternehmen/betriebsnummern-service/taetigkeitsschluessel");
const html = await response.text();
const hast = fromHtml(html);


const $ = cherio.load(html);
//console.log($('h3:contains("Download aktuelle Version")').html());
//console.log($('h3:contains("Download aktuelle Version")').parent().children('p + p').text());
console.log($('h3:contains("Download aktuelle Version")').parent().children('p + p'));

