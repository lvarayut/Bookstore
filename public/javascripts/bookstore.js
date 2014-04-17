$(document).ready(function() {
    $('#introduction').fullpage({
        verticalCentered: false,
        resize : true,
        slidesColor : [],
        anchors:['sign-up', 'showcase','contact-us'],
        scrollingSpeed: 700,
        easing: 'easeInQuart',
        menu: false,
        navigation: true,
        navigationPosition: 'right',
        'navigationColor': '#000',
        navigationTooltips: [],
        slidesNavigation: false,
        slidesNavPosition: 'bottom',
        'controlArrowColor': '#fff',
        loopBottom: false,
        loopTop: false,
        loopHorizontal: true,
        autoScrolling: true,
        scrollOverflow: false,
        css3: false,
        paddingTop: 0,
        paddingBottom: 0,
        fixedElements: null,
        normalScrollElements: null,
        keyboardScrolling: true,
        touchSensitivity: 5,
        continuousVertical: false,
        animateAnchor: true,
        'normalScrollElementTouchThreshold': 5,


        //events
        onLeave: function(index, direction){},
        afterLoad: function(anchorLink, index){},
        afterRender: function(){},
        afterSlideLoad: function(anchorLink, index, slideAnchor, slideIndex){},
        onSlideLeave: function(anchorLink, index, slideIndex, direction){}
    });
});