function Item(name) {
  return {name}
}

function delayedResolveTo(items, timeout) {
  return new Promise((resolve, reject) => {
    setTimeout(function () {
      resolve(items);
    }, timeout);
  });
}

export function fetchSomeItems() {
  const itemNames = ['one', 'two', 'three'];
  const items = itemNames.map(it => new Item(it));
  return delayedResolveTo(items, 1000);
}