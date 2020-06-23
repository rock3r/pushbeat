async function refreshBeatValue(id = '') {
    const response = await fetch("/beat/" + id + "/refresh", {
        method: 'GET',
        cache: 'no-cache',
        headers: {
            'Authentication': 'Basic push beat'
        }
    });
    return response.json();
}

