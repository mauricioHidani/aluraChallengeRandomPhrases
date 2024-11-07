const baseURL = 'http://localhost:8080';

export default function postFrase(endpoint) {
    const url = `${baseURL}${endpoint}`;
    return fetch(url, { 
            method: 'POST', 
            headers: { 'Content-Type': 'application/json' }, 
        })
        .then(response => response.json())
        .catch(error => { console.error(error) });
}
