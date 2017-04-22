$(document).ready(function() {
  console.log('LOADED');
  AjaxRequest();
});

var AjaxRequest = function() {
  var req = $.ajax({
    type: "GET",
    url: "rest/quizzes",
    dataType: "json"
  });

  req.done(function(data, status) {
    console.log('GREAT SUCCESS DOM');
    console.log(data);
    buildQuizTable(data);
  });

  req.fail(function(xhr, status, error) {
    console.log("FAILED");
  });
}

var buildQuizTable = function(data) {
  var $createButton = $('<button>Create Quiz</button>');
  $createButton.attr('id', 'create');
  $createButton.click(function() {
    createQuiz();
  });

  var $table = $('<table>');
  $table.attr('id', 'quiztable')
  var $thead = $('<thead>');
  var $tbody = $('<tbody>');
  var $tr1 = $('<tr>');
  var $th1 = $('<th>');
  var $th2 = $('<th>');
  var $th3 = $('<th>');

  $th1.text('Quiz Name');
  $th2.text('View');
  $th3.text('Edit');

  $tr1.append($th1, $th2, $th3);
  $thead.append($tr1);
  $table.append($thead);

  data.forEach(function(v) {
    var $tr2 = $('<tr>');
    var $td1 = $('<td>');
    var $viewButton = $('<button>View</button>');
    $viewButton.attr('name', v.id);
    var $editButton = $('<button>Edit</button>');
    $editButton.attr('name', v.id);
    var $deleteButton = $('<button>Delete</button>')
    $deleteButton.attr('name', v.id);

    $viewButton.click(function() {
      $table.hide();
      var $btlButton = $('<button>Back to List</button>');
      var $h1 = $('<h1>');
      $h1.attr('id', 'h1')
      $h1.text(v.name);
      // var press = $(this).text();
      // if(press == 'View') {
      //   console.log(v);
      //   $(this).text(v.name);
      // }
      // else {
      //   $(this).text('View')
      // }
      displaySingleQuiz(v.id, $(this));

      $btlButton.click(function(e) {
        $('#olid').hide();
        $btlButton.hide();
        $('#h1').remove();
        AjaxRequest();
      });
      $('body').append($h1);
      $('body').append($btlButton);
    });

    $editButton.click(function() {
      editQuiz(v.id, v);
    });

    $deleteButton.click(function(e) {
      e.preventDefault();
      if (window.confirm("Do you really want to delete?")) {
        deleteQuiz(v.id, $(this));
      }
    });

    $td1.text(v.name);
    $tr2.append($td1, $viewButton, $editButton, $deleteButton);
    $tbody.append($tr2);
  });

  $table.append($tbody);
  $('body').append($createButton);
  $('body').append($table);
}

var displaySingleQuiz = function(id, btn) {

  var req = $.ajax({
    type: "GET",
    url: "rest/quizzes/" + id + "/questions",
    dataType: "json"
  });

  req.done(function(data, status) {
    console.log('GREAT SUCCESS DISPLAY SINGLE QUIZ');
    console.log(data);
    $(create).remove();

    var $ol = $('<ol>');
    $ol.attr('id', 'olid')

    data.forEach(function(v) {
      console.log('IN FOREACH');
      var $li = $('<li>');
      $li.text(v.questionText);
      $ol.append($li);
    });

    $('body').append($ol);
  });

  req.fail(function(xhr, status, error) {
    console.log("FAILED");
  });
}

var createQuiz = function() {
  var $form = $('<form name="create">');
  $form.attr('id', 'create')
  var $input = $('<input>');
  $input.attr('placeholder', 'Quiz Name');
  $input.attr('type', 'text');
  $input.attr('name', 'quizName');
  var $button = $('<button>Submit</button>');

  $form.append($input, $button);
  $('body').prepend($form);

  $button.click(function(e) {
    e.preventDefault();

    var quiz = {
      name: $(document.create.quizName).val(),
      // name: $(create.name).val(),
      // height: $(create.height).val(),
      // weight: $(create.weight).val(),
      // img: $(create.img).val(),
      // description: $(create.description).val(),
    };

    console.log(quiz);

    var req = $.ajax({
      type: "POST",
      url: "rest/quizzes/", //indicates creating a new quiz
      dataType: "json",
      contentType: 'application/json', //setting the request headers content-type
      data: JSON.stringify(quiz)
    });

    req.done(function(data, status) {
      console.log('GREAT SUCCESS FORM');
      $(create).remove();
      $('#quiztable').remove();
      AjaxRequest();
    });

    req.fail(function(xhr, status, error) {
      console.log("CREATE QUIZ FAILED");
    });
  });
}

var editQuiz = function(id, data) {
  var $form = $('<form name="edit">');
  $form.attr('id', 'create')
  var $input = $('<input>');
  $input.attr('value', data.name);
  $input.attr('type', 'text');
  $input.attr('name', 'quizName');
  var $button = $('<button>Submit</button>');

  $form.append($input, $button);
  $('body').prepend($form);

  $button.click(function(e) {
    e.preventDefault();

    var quiz = {
      name: $(edit.quizName).val(),
      // name: $(create.name).val(),
      // height: $(create.height).val(),
      // weight: $(create.weight).val(),
      // img: $(create.img).val(),
      // description: $(create.description).val(),
    };

    var req = $.ajax({
      type: "PUT",
      url: "rest/quizzes/" + id,
      dataType: "json",
      contentType: 'application/json',
      data: JSON.stringify(quiz)
    });

    req.done(function(data, status) {
      console.log('GREAT SUCCESS EDIT');
      (edit).remove();
      $('#create').remove();
      $('#quiztable').remove();
      AjaxRequest();
    });

    req.fail(function(xhr, status, error) {
      console.log("FAILED");
    });

  });
}

var deleteQuiz = function(id, btn) {
  var req = $.ajax({
    type: "DELETE",
    url: "rest/quizzes/" + id
  });

  req.done(function(data, status) {
    console.log('GREAT SUCCESS DELETE');
    btn.parent().remove();
  });

  req.fail(function(xhr, status, error) {
    console.log("DELETE FAILED");
  });
}
