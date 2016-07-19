function login() {
  var userId = $('.user-id').val();
  $.getJSON('http://localhost:8898/auth/token/'+userId)
    .then(function (token) {
      Cookies.set('auth_token', token.token);
      Cookies.set('auth_user', token.userId);
      setLoginStatus();
    })
    .fail(function (xhr) {
      switch (xhr.status) {
        case 404:
              setMessage("No user ID provided");
              break;
        case 401:
              setMessage("Invalid user id");
              break;
        default:
              setMessage("Unknown Error");
      }
    });
}

function logout() {
  Cookies.remove('auth_token');
  Cookies.remove('auth_user');
  setLoginStatus();
}

function createUser() {
  var userData = {
    name: $('.new-user-name').val(),
    role: $('.user-role').val()
  };

  $.post({
    url: '/api/tenants/'+ tenantId() +'/users',
    contentType: 'application/json',
    data: JSON.stringify(userData)
  })
    .then(function (data) {
      setMessage("Created user: " + JSON.stringify(data));
    })
    .fail(standardErrorHandler);
}

function showPublic() {
  $.getJSON('/api/tenants/'+ tenantId() +'/widgets/public')
    .then(function (widgets) {
      showWidgets(widgets);
    })
    .fail(standardErrorHandler)
}

function showPrivate() {
  $.getJSON('/api/tenants/'+ tenantId() +'/widgets/private')
    .then(function (widgets) {
      showWidgets(widgets);
    })
    .fail(standardErrorHandler)
}

function showSecret() {
  $.getJSON('/api/tenants/'+ tenantId() +'/widgets/top-secret')
    .then(function (widgets) {
      showWidgets(widgets);
    })
    .fail(standardErrorHandler)
}

function createWidget() {
  var widgetData = {
    name: $('.widget-name').val(),
    scope: $('.widget-scope').val()
  };

  $.post({
    url: '/api/tenants/'+ tenantId() +'/widgets',
    contentType: 'application/json',
    data: JSON.stringify(widgetData)
  })
    .then(function (data) {
      setMessage("Created widget: " + JSON.stringify(data));
    })
    .fail(standardErrorHandler);
}

function setMessage(message) {
  $('.message').html(message);
  setTimeout(function () {
    $('.message').html("");
  }, 10000);
}

function setLoginStatus() {
  if (Cookies.get('auth_token') && Cookies.get('auth_user')) {
    $('.login-status').html("Logged in as user id: " + Cookies.get('auth_user'));
  } else {
    $('.login-status').html("Not Logged In (use user ID 1, 10, or 20 if you haven't created another)");
  }
}

function showWidgets(widgets) {
  var list = $('.widget-list');

  list.html("<ol></ol>");
  for (var i = 0; i < widgets.length; i++) {
    list.find('ol').append('<li>'+JSON.stringify(widgets[i])+'</li>');
  }
}

function standardErrorHandler(xhr) {
  switch (xhr.status) {
    case 401:
      setMessage("Not logged in!");
      break;
    case 403:
      setMessage("Missing required role");
      break;
    default:
      setMessage("Unknown Error");
  }
}

function tenantId() {
  return parseInt($('.tenant-id').val());
}

function init() {
  setLoginStatus();
}

$(init);
