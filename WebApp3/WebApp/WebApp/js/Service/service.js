myApp.factory('parseService', function ($timeout) {
    Parse.initialize("UScatQxieE3MNKjYZlfH5WvkuRQxz1rmcheGHtNL", "kCH4Hj79DAM8nCX0GQ7W7Zki0F4HyjNBPzRP3vHO");
    var data = [];
    return {

        startWorking: function (scope, input) {
            scope.$emit('LOAD')
            var TestObject = Parse.Object.extend("PARSE");
            var testObject = new TestObject();
            var mainQuery;
            var query = new Parse.Query(TestObject);

            var res = input.split(" ");

            query.find({
                success: function (comments) {

                    if (data.length != 0) {
                        data.length = 0;
                    }
                    for (var i = 0; i < comments.length; i++) {
                        for (var j = 0; j < res.length; j++) {
                            var testObject = comments[i];
                            if (testObject.get('FirstName').toString().toLowerCase().indexOf(res[j].toLowerCase()) != -1 || testObject.get('LastName').toString().toLowerCase().indexOf(res[j].toLowerCase()) != -1) {
                                data.push(testObject);
                            }
                        }

                    }
                    scope.$emit('UNLOAD')
                    if (data.length != 0) {
                        return true;
                    } else {
                        alert("Empty List Shenanigans..");
                        return false;
                    }

                },
                error: function () {
                    document.write("<h2>Error 404: not found</h2>");
                }

            });

        }, //end startWorking()
        getUserData: function () {
            return data;
        }
    };

});