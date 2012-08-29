$(document).ready(function() {
  
  $("'[placeholder]'").parents("'form'").submit(function() {
    $(this).find("'[placeholder]'").each(function() {
      var input = $(this);
      if (input.val() == input.attr("'placeholder'")) {
        input.val("''");
      }
    })
  });
  
  
  var controller = $.scrollorama({
    blocks: '.hero',
    enablePin: false
  });
  $('.scrollblock').css('position', 'relative').css('top', '0');

  controller.animate(
      '.t-phone',
      { 
        delay: 0, 
        duration: 800, 
        property:'top', 
        start:135, 
        end:600
      }
    );
  controller.animate(
      '.t-ipad',
      { 
        delay: 0, 
        duration: 800, 
        property:'top', 
        start:139, 
        end:500 
      }
    );
  controller.animate(
      '.t-stage',
      { 
        delay: 0, 
        duration: 800, 
        property:'top', 
        start:0, 
        end:500 
      }
    );
  
  //colorbox
  $("a.cb").colorbox({iframe:true, innerWidth:900, innerHeight:600});
  
  var duration = '5000';
  var timer;
  clearTimeout(timer);
  timer = setInterval(eval("nextItem"), duration);
  
  $('.q-pager .dot').click(function() {
    if ($(this).hasClass('active')) {
      clearTimeout(timer);
      timer = setInterval(eval("nextItem"), duration);
    }
    else {
      var item = $(this).attr('item');
      $('.q-pager .active').removeClass('active');
      $(this).addClass('active');
      $('.quote.active').removeClass('active');
      $('.quote:nth-child('+item+')').addClass('active');
      clearTimeout(timer);
      timer = setInterval(eval("nextItem"), duration);
    }
  });


  $('.header nav ul li a, .free-quote, .mini-nav a, .heroleft a').click(function() {
     var elementClicked = $(this).attr("href");
     if (elementClicked == '#top') {
       destination = 0;
     }
     if (elementClicked == '#what-we-do') {
       destination = 150;
     }
     if (elementClicked == '#how-we-do-it') {
       destination = 770;
     }
     if (elementClicked == '#projects') {
       destination = 1440;
     }
     if (elementClicked == '#free-quote') {
       destination = 2110;
     }
     $("html:not(:animated),body:not(:animated)").animate({ scrollTop: destination-20}, 500 );
     return false;
  });
  
  //projects - right
  $(".projects .controls .right").click(function() {
    var items = 2;
    var active = $(".projects .controls .active");
    var active_num = parseInt(active.attr('item'));
    if (items == active_num) {
      $(".project-items ul").animate({left: '0px'}, 500);
      $(".projects .controls .dot").removeClass('active');
      $(".projects .controls .dot:first").addClass('active');
    }
    else {
      var length = -(756 * (active_num)) + 'px';
      $(".project-items ul").animate({left: length}, 500);
      $(".projects .controls .dot").removeClass('active');
      $(".projects .controls .dot:nth-child("+(active_num+2)+")").addClass('active');
    }
  });
  
  //projects - left
  $(".projects .controls .left").click(function() {
    var items = 2;
    var active = $(".projects .controls .active");
    var active_num = parseInt(active.attr('item'));
    if (1 == active_num) {
      var length = -(756 * (items - 1)) + 'px';
      $(".project-items ul").animate({left: length}, 500);
      $(".projects .controls .dot").removeClass('active');
      $(".projects .controls .dot:last").addClass('active');
    }
    else {
      var length = -(756 * (active_num-2)) + 'px';
      $(".project-items ul").animate({left: length}, 500);
      $(".projects .controls .dot").removeClass('active');
      $(".projects .controls .dot:nth-child("+(active_num)+")").addClass('active');
    }
  });
  
  //projects - dots
  $(".projects .controls .dot").click(function() {
      if ($(this).hasClass('active')) {
        return false;
      }
      else {
        var item = $(this).attr('item');
        $('.projects .controls .dot.active').removeClass('active');
        $(this).addClass('active');
        var length = -(756 * (item - 1)) + 'px';
        $(".project-items ul").animate({left: length}, 500);
      }
  });
  
  //clients - right
  $(".clients .controls .right").click(function() {
    var items = 5;
    var active = $(".clients .controls .active");
    var active_num = parseInt(active.attr('item'));
    if (items == active_num) {
      $(".client-items ul").animate({left: '0px'}, 500);
      $(".clients .controls .dot").removeClass('active');
      $(".clients .controls .dot:first").addClass('active');
    }
    else {
      var length = -(734 * (active_num)) + 'px';
      $(".client-items ul").animate({left: length}, 500);
      $(".clients .controls .dot").removeClass('active');
      $(".clients .controls .dot:nth-child("+(active_num+2)+")").addClass('active');
    }
  });
  
  //clients - left
  $(".clients .controls .left").click(function() {
    var items = 5;
    var active = $(".clients .controls .active");
    var active_num = parseInt(active.attr('item'));
    if (1 == active_num) {
      var length = -(734 * (items - 1)) + 'px';
      $(".client-items ul").animate({left: length}, 500);
      $(".clients .controls .dot").removeClass('active');
      $(".clients .controls .dot:last").addClass('active');
    }
    else {
      var length = -(734 * (active_num-2)) + 'px';
      $(".client-items ul").animate({left: length}, 500);
      $(".clients .controls .dot").removeClass('active');
      $(".clients .controls .dot:nth-child("+(active_num)+")").addClass('active');
    }
  });
  
  //client - dots
  $(".clients .controls .dot").click(function() {
      if ($(this).hasClass('active')) {
        return false;
      }
      else {
        var item = $(this).attr('item');
        $('.clients .controls .dot.active').removeClass('active');
        $(this).addClass('active');
        var length = -(734 * (item - 1)) + 'px';
        $(".client-items ul").animate({left: length}, 500);
      }
  });
  
});

var nextItem = function() {
  var items = 3;
  
  var active_slide = $(".quote.active");
  var active_dot = $(".q-pager .active");
  var item = parseInt(active_dot.attr('item'));
  active_slide.removeClass('active');
  active_dot.removeClass('active');
  
  if (items == item) {
    $('.quote:first').addClass('active');
    $(".q-pager .dot:first").addClass('active');
  }
  else {
    var next_slide = active_slide.next();
    var next_dot = active_dot.next();
    next_slide.addClass('active');
    next_dot.addClass('active');
  }
  
}