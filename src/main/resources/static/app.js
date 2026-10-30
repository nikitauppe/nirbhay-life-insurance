const $ = id => document.getElementById(id);

// Build the Basic auth header (works with any characters in the password)
function basicHeader(username, password) {
  const bytes = new TextEncoder().encode(username + ':' + password);
  let bin = '';
  bytes.forEach(b => bin += String.fromCharCode(b));
  return 'Basic ' + btoa(bin);
}

// fetch wrapper: adds the login header and handles expired/invalid login
async function api(url, options = {}) {
  const headers = Object.assign({}, options.headers || {});
  const auth = sessionStorage.getItem('auth');
  if (auth) headers['Authorization'] = auth;
  if (options.body) headers['Content-Type'] = 'application/json';
  const res = await fetch(url, Object.assign({}, options, { headers }));
  if (res.status === 401) logout();
  return res;
}

function logout() {
  sessionStorage.clear();
  window.location.href = 'index.html';
}

// Returns the logged-in user only if the role matches, else goes to login
function requireRole(role) {
  let me = null;
  try { me = JSON.parse(sessionStorage.getItem('me')); } catch (e) { me = null; }
  if (!me || me.role !== role) {
    window.location.href = 'index.html';
    return null;
  }
  return me;
}

function cell(tr, text) {
  const td = document.createElement('td');
  td.textContent = text ?? '';
  tr.appendChild(td);
}

function btn(label, cls, handler) {
  const b = document.createElement('button');
  b.textContent = label;
  if (cls) b.className = cls;
  b.onclick = handler;
  return b;
}