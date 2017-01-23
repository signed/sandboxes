import {Injectable} from 'angular2/core';
import {Http, Response} from 'angular2/http';
import 'rxjs/add/operator/map';

@Injectable()
export class SearchService {
  constructor(private http:Http) {
  }

  search(q:string) {
    if (!q || q === '*') {
      q = '';
    } else {
      q = q.toLowerCase();
    }
    return this.getAll().map((data: Array<any>) => {
      let results:any[] = [];
      data.map(item => {
        if (JSON.stringify(item).toLowerCase().includes(q)) {
          results.push(item);
        }
      });
      return results;
    });
  }

  getAll() {
    return this.http.get('shared/data/people.json').map((res:Response) => res.json());
  }
}

export class Address {
  street:string;
  city:string;
  state:string;
  zip:string;

  constructor(obj?:any) {
    this.street = obj && obj.street || null;
    this.city = obj && obj.city || null;
    this.state = obj && obj.state || null;
    this.zip = obj && obj.zip || null;
  }
}

export class Person {
  id:number;
  name:string;
  phone:string;
  address:Address;

  constructor(obj?:any) {
    this.id = obj && Number(obj.id) || null;
    this.name = obj && obj.name || null;
    this.phone = obj && obj.phone || null;
    this.address = obj && obj.address || null;
  }
}
