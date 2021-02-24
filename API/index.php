<?php


use \Psr\Http\Message\ServerRequestInterface as Request;
use \Psr\Http\Message\ResponseInterface as Response;

require '../vendor/autoload.php';

require '../includes/DbOperations.php';

$app = new \Slim\App([
    'settings'=>[
        'displayErrorDetails'=>true
    ]
]);


$app->add(new Tuupola\Middleware\HttpBasicAuthentication([
    "secure"=>false,
    "users" => [
        "myusersamuel" => "123456",
    ]
]));

//CREAR USUARIO
$app->post('/createuser', function(Request $request, Response $response){

    if(!haveEmptyParameters(array('email', 'password', 'nombre_usuario', 'telefono'), $request, $response)){

        $request_data = $request->getParsedBody(); 

        $email = $request_data['email'];
        $password = $request_data['password'];
        $nombre_usuario = $request_data['nombre_usuario'];
        $telefono = $request_data['telefono']; 

        $hash_password = password_hash($password, PASSWORD_DEFAULT);

        $db = new DbOperations; 

        $result = $db->createUser($email, $hash_password, $nombre_usuario, $telefono);
        
        if($result == USER_CREATED){

            $message = array(); 
            $message['error'] = false; 
            $message['message'] = 'Usuario creado correctamente';

            $response->write(json_encode($message));

            return $response
                        ->withHeader('Content-type', 'application/json')
                        ->withStatus(201);

        }else if($result == USER_FAILURE){

            $message = array(); 
            $message['error'] = true; 
            $message['message'] = 'Debe haber algun error';

            $response->write(json_encode($message));

            return $response
                        ->withHeader('Content-type', 'application/json')
                        ->withStatus(422);    

        }else if($result == USER_EXISTS){
            $message = array(); 
            $message['error'] = true; 
            $message['message'] = 'Email/Nombre usario ya existe';

            $response->write(json_encode($message));

            return $response
                        ->withHeader('Content-type', 'application/json')
                        ->withStatus(422);    
        }
    }
    return $response
        ->withHeader('Content-type', 'application/json')
        ->withStatus(422);    
});

//LOGIN DEL USUARIO
$app->post('/userlogin', function(Request $request, Response $response){

    if(!haveEmptyParameters(array('email', 'password'), $request, $response)){
        $request_data = $request->getParsedBody(); 

        $email = $request_data['email'];
        $password = $request_data['password'];

        $db = new DbOperations;

        $result = $db->userLogin($email, $password);

        if($result == USER_AUTHENTICATED){
            
            $user = $db->getUserByEmail($email); 

            $response_data = array();

            $response_data['error'] = false;
            $response_data['message'] = 'Logueado exitosamente';
            $response_data['user'] = $user;

            $response->write(json_encode($response_data));

            return $response
                        ->withHeader('Content-type', 'application/json')
                        ->withStatus(200);  

        }else if($result == USER_NOT_FOUND){

            $response_data = array();

            $response_data['error'] = true;
            $response_data['message'] = 'Usuario no existe';

            $response->write(json_encode($response_data));

            return $response
                        ->withHeader('Content-type', 'application/json')
                        ->withStatus(200);  

        }else if($result == USER_PASSWORD_DO_NOT_MATCH){

            $response_data = array();

            $response_data['error'] = true;
            $response_data['message'] = 'Contraseña incorrecta';

            $response->write(json_encode($response_data));

            return $response
                        ->withHeader('Content-type', 'application/json')
                        ->withStatus(200);  

        }
    }

    return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(422);    

});

//Hacer un UPDATE DATOS USUARIO
$app->put('/updateuser/{id_usuario}', function(Request $request, Response $response, array $args){
    
    $id_usuario = $args['id_usuario'];

    if(!haveEmptyParameters(array('email','nombre_usuario','telefono'), $request, $response)){

        $request_data = $request->getParsedBody(); 

        $email = $request_data['email'];
        $nombre_usuario = $request_data['nombre_usuario'];
        $telefono = $request_data['telefono']; 
        

        $db = new DbOperations;

        if($db->updateUser($email, $nombre_usuario, $telefono, $id_usuario)){
            $response_data = array();
            $response_data['error'] = false;
            $response_data['message'] = 'Usuario actualizado correctamente';

            $user = $db->getUserByEmail($email);
            $response_data['user'] = $user;

            $response->write(json_encode($response_data));

            return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(200); 
        }else{
            $response_data = array();
            $response_data['error'] = true;
            $response_data['message'] = 'Vuelva a intentarlo más tarde';

            $user = $db->getUserByEmail($email);
            $response_data['user'] = $user;

            $response->write(json_encode($response_data));

            return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(200); 
        }

    }

    return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(200); 

});

//HACER UPDATE PASSWORD
$app->put('/updatepassword', function(Request $request, Response $response){

    if(!haveEmptyParameters(array('currentpassword','newpassword','email'), $request, $response)){
        $request_data = $request->getParsedBody();

        $currentpassword = $request_data['currentpassword'];
        $newpassword = $request_data['newpassword'];
        $email = $request_data['email'];

        $db = new DbOperations;

        $result = $db->updatePassword($currentpassword, $newpassword, $email);

        if($result == PASSWORD_CHANGED){
            $response_data = array();
            $response_data['error'] = false;
            $response_data['message'] = 'Password Modificada';
            $response->write(json_encode($response_data));
            return $response
                        ->withHeader('Content-type', 'application/json')
                        ->withStatus(200);
        }else if($result == PASSWORD_DO_NOT_MATCH){
            $response_data = array();
            $response_data['error'] = true;
            $response_data['message'] = 'Has introducido una password erronea';
            $response->write(json_encode($response_data));
            return $response
                        ->withHeader('Content-type', 'application/json')
                        ->withStatus(200);
        }else if($result == PASSWORD_NOT_CHANGED){
            $response_data = array();
            $response_data['error'] = true;
            $response_data['message'] = 'Ha ocurrido un error';
            $response->write(json_encode($response_data));
            return $response
                        ->withHeader('Content-type', 'application/json')
                        ->withStatus(200);
        }

    }

    return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(200); 

});

//RECUPERAR PASSWORD
$app->put('/restorepassword', function(Request $request, Response $response){

    if(!haveEmptyParameters(array('newpassword','email'), $request, $response)){
        $request_data = $request->getParsedBody();

        $newpassword = $request_data['newpassword'];
        $email = $request_data['email'];

        $db = new DbOperations;

        $result = $db->restorePassword($newpassword, $email);

        if($result == PASSWORD_CHANGED){
            $response_data = array();
            $response_data['error'] = false;
            $response_data['message'] = 'Comprueba tu email';
            $response->write(json_encode($response_data));
            return $response
                        ->withHeader('Content-type', 'application/json')
                        ->withStatus(200);
        }else if($result == PASSWORD_NOT_CHANGED){
            $response_data = array();
            $response_data['error'] = true;
            $response_data['message'] = 'Ha ocurrido un error';
            $response->write(json_encode($response_data));
            return $response
                        ->withHeader('Content-type', 'application/json')
                        ->withStatus(200);
        }

    }

    return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(200); 

});

//COMPROBAR SI EXISTE USUARIO
$app->get('/existuser/{email}', function(Request $request, Response $response, array $args){

    $email = $args['email'];

    $db = new DbOperations;

    $result = $db->isEmailExist($email);

    if($result == true){
        $user = $db->getUserExist($email);

        $response_data = array();
    
        $response_data['error'] = false;
        $response_data['user'] = $user;
    
        $response->write(json_encode($response_data));
    
        return $response
                    ->withHeader('Content-type', 'application/json')
                    ->withStatus(200);
    }else{
        $message = array(); 
        $message['error'] = true; 
        $message['message'] = 'No existe ese usuario';

        $response->write(json_encode($message));

        return $response
                    ->withHeader('Content-type', 'application/json')
                    ->withStatus(422); 
    }


});

//BORRAR USUARIO
$app->delete('/deleteuser/{id_usuario}', function(Request $request, Response $response, array $args){

    $id_usuario = $args['id_usuario'];

    $db = new DbOperations;

    $response_data = array();

    if($db->deleteUser($id_usuario)){
        $response_data['error'] = false;
        $response_data['message'] = 'Usuario ha sido borrado';
    }else{
        $response_data['error'] = true;
        $response_data['message'] = 'Vuelva a intentarlo';
    }

    $response->write(json_encode($response_data));
    
    return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(200);
});

//Recoger usuarios
$app->get('/librosteoria/{tematica}', function(Request $request, Response $response, array $args){

    $tematica = $args['tematica'];

    $db = new DbOperations;

    $libros = $db->getAllLibrosT($tematica);

    $response_data = array();

    $response_data['error'] = false;
    $response_data['libros'] = $libros;

    $response->write(json_encode($response_data));

    return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(200); 

});

//Recoger usuarios
$app->get('/librosejercicios/{tematica}', function(Request $request, Response $response, array $args){

    $tematica = $args['tematica'];

    $db = new DbOperations;

    $ejercicios = $db->getAllEjerciciosT($tematica);

    $response_data = array();

    $response_data['error'] = false;
    $response_data['ejercicios'] = $ejercicios;

    $response->write(json_encode($response_data));

    return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(200); 

});

//Recoger usuarios
$app->get('/videotutoriales/{tematica}', function(Request $request, Response $response, array $args){

    $tematica = $args['tematica'];

    $db = new DbOperations;

    $videos = $db->getAllVideosT($tematica);

    $response_data = array();

    $response_data['error'] = false;
    $response_data['videos'] = $videos;

    $response->write(json_encode($response_data));

    return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(200); 

});

//Recoger usuarios
$app->get('/allusers', function(Request $request, Response $response){

    $db = new DbOperations;

    $users = $db->getAllUsers();

    $response_data = array();

    $response_data['error'] = false;
    $response_data['users'] = $users;

    $response->write(json_encode($response_data));

    return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(200); 

});

//Recoger un usuario
$app->get('/oneuser/{email}', function(Request $request, Response $response, array $args){

    $emailuser = $args['email'];

    $db = new DbOperations;

    $users = $db->getOneUser($emailuser);

    $response_data = array();

    $response_data['error'] = false;
    $response_data['users'] = $users;

    $response->write(json_encode($response_data));

    return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(200); 

});

//GUARDAR MENSAJE
$app->post('/guardarmensajes', function(Request $request, Response $response){

    if(!haveEmptyParameters(array('ip_usuario', 'nombre_usuario', 'email', 'mensaje'), $request, $response)){

        $request_data = $request->getParsedBody(); 

        $ip_usuario = $request_data['ip_usuario'];
        $nombre_usuario = $request_data['nombre_usuario'];
        $email = $request_data['email'];
        $mensaje = $request_data['mensaje']; 

        $db = new DbOperations; 

        $result = $db->saveMessage($ip_usuario, $nombre_usuario, $email, $mensaje);
        
        if($result == USER_CREATED){

            $message = array(); 
            $message['error'] = false; 
            $message['message'] = 'Enviado';

            $response->write(json_encode($message));

            return $response
                        ->withHeader('Content-type', 'application/json')
                        ->withStatus(201);

        }else if($result == USER_FAILURE){

            $message = array(); 
            $message['error'] = true; 
            $message['message'] = 'Debe haber algun error';

            $response->write(json_encode($message));

            return $response
                        ->withHeader('Content-type', 'application/json')
                        ->withStatus(424);    

        }else if($result == USER_EXISTS){
            $message = array(); 
            $message['error'] = true; 
            $message['message'] = 'Usuario no existe';

            $response->write(json_encode($message));

            return $response
                        ->withHeader('Content-type', 'application/json')
                        ->withStatus(423);    
        }
    }
    return $response
        ->withHeader('Content-type', 'application/json')
        ->withStatus(422);    
});

//Guardar Amigo
$app->post('/guardaramigo', function(Request $request, Response $response){

    if(!haveEmptyParameters(array('nombre_user', 'nombre_user_amigo'), $request, $response)){

        $request_data = $request->getParsedBody(); 

        $nombre_user = $request_data['nombre_user'];
        $nombre_user_amigo = $request_data['nombre_user_amigo'];
       
        $db = new DbOperations; 

        $result = $db->saveAmigo($nombre_user, $nombre_user_amigo);
        
        if($result == USER_CREATED){

            $message = array(); 
            $message['error'] = false; 
            $message['message'] = '¡Enhorabuena! Ahora sois amigos.';

            $response->write(json_encode($message));

            return $response
                        ->withHeader('Content-type', 'application/json')
                        ->withStatus(201);

        }else if($result == USER_FAILURE){

            $message = array(); 
            $message['error'] = true; 
            $message['message'] = 'No se ha guardado';

            $response->write(json_encode($message));

            return $response
                        ->withHeader('Content-type', 'application/json')
                        ->withStatus(422);    

        }else if($result == USER_EXISTS){
            $message = array(); 
            $message['error'] = true; 
            $message['message'] = '¡Ya es tu amigo!';

            $response->write(json_encode($message));

            return $response
                        ->withHeader('Content-type', 'application/json')
                        ->withStatus(422);    
        }
    }
    return $response
        ->withHeader('Content-type', 'application/json')
        ->withStatus(422);    
});

//Mostrar mensajes
$app->get('/allmessages', function(Request $request, Response $response){

    $db = new DbOperations;

    $messages = $db->getAllMessages();

    $response_data = array();

    $response_data['error'] = false;
    $response_data['messages'] = $messages;

    $response->write(json_encode($response_data));

    return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(200); 

});

//LOGIN DEL USUARIO
$app->post('/createchat', function(Request $request, Response $response){

    // Use ls command to shell_exec 
    // function 
    
    $output = shell_exec('php AcademyForo/bin/server.php'); 
    
    // Display the list of all file 
    // and directory 
    echo "<pre>$output</pre>"; 


});

//Mostrar Amigos
$app->get('/allamigos/{nombre_user}', function(Request $request, Response $response, array $args ){

    $nombre_user = $args['nombre_user'];

    $db = new DbOperations;

    $users = $db->getAllFriends($nombre_user);

    $response_data = array();

    $response_data['error'] = false;
    $response_data['users'] = $users;

    $response->write(json_encode($response_data));

    return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(200); 

});



//comprobación de campos
function haveEmptyParameters($required_params, $request, $response){
    $error = false; 
    $error_params = '';
    $request_params = $request->getParsedBody(); 

    foreach($required_params as $param){
        if(!isset($request_params[$param]) || strlen($request_params[$param])<=0){
            $error = true; 
            $error_params .= $param . ', ';
        }
    }

    if($error){
        $error_detail = array();
        $error_detail['error'] = true; 
        $error_detail['message'] = 'Parametros requeridos: ' . substr($error_params, 0, -2) . ', faltan o estan vacios';
        $response->write(json_encode($error_detail));
    }
    return $error; 
}

$app->run();

