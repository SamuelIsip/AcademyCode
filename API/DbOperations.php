<?php 

    class DbOperations{

        private $con; 

        function __construct(){
            require_once dirname(__FILE__) . '/DbConnect.php';
            $db = new DbConnect; 
            $this->con = $db->connect(); 
        }

        //INSERTAR USUARIO
        public function createUser($email, $password, $nombre_usuario, $telefono){
           if(!$this->isEmailExist($email) && !$this->isNomUserExist($nombre_usuario)){
                $stmt = $this->con->prepare("INSERT INTO usuario 
                (email, password, nombre_usuario, telefono, fecha) VALUES (?, ?, ?, ?, ?)");
                $fecha = date('d/m/Y'); //fecha sistema
                $stmt->bind_param("sssss", $email, $password, $nombre_usuario, $telefono, $fecha);
                if($stmt->execute()){
                    return USER_CREATED; 
                }else{
                    return USER_FAILURE;
                }
           }
           return USER_EXISTS; 
        }

        public function userLogin($email, $password){

            if($this->isEmailExist($email)){
                $hashed_password = $this->getUsersPasswordByEmail($email);
                if(password_verify($password, $hashed_password)){//se verifica la pass hasheada es correcta
                    return USER_AUTHENTICATED;
                }else{
                    return USER_PASSWORD_DO_NOT_MATCH;
                }

            }else{
                return USER_NOT_FOUND;
            }

        }

         //UPDATE usuario
         public function updateUser($email, $nombre_usuario, $telefono, $id_usuario){

            $stmt = $this->con->prepare("UPDATE usuario SET email = ?, nombre_usuario = ?, telefono = ? WHERE id_usuario = ?");
            $stmt->bind_param("sssi", $email, $nombre_usuario, $telefono, $id_usuario); //s-string i -number
            if($stmt->execute()){
                return true;
            }
            return false;
        }

        //UPDATE password
        public function updatePassword($currentpassword, $newpassword, $email){

            $hashed_password = $this->getUsersPasswordByEmail($email);

            if(password_verify($currentpassword, $hashed_password)){//se verifica la pass hasheada es correcta
               
                $hash_password = password_hash($newpassword, PASSWORD_DEFAULT);
                $stmt = $this->con->prepare("UPDATE usuario SET password = ? WHERE email = ?");
                $stmt->bind_param("ss",$hash_password, $email);

                if($stmt->execute()){
                    return PASSWORD_CHANGED;
                }else{
                    return PASSWORD_NOT_CHANGED;
                }

            }else{
                return PASSWORD_DO_NOT_MATCH;
            }
        
        }

        //RESTORE password
        public function restorePassword($newpassword, $email){

            $hashed_password = $this->getUsersPasswordByEmail($email);
               
                $hash_password = password_hash($newpassword, PASSWORD_DEFAULT);
                $stmt = $this->con->prepare("UPDATE usuario SET password = ? WHERE email = ?");
                $stmt->bind_param("ss",$hash_password, $email);

                if($stmt->execute()){
                    return PASSWORD_CHANGED;
                }else{
                    return PASSWORD_NOT_CHANGED;
                }    
        
        }

        //DELETE USER
        public function deleteUser($id_usuario){
            $stmt = $this->con->prepare("DELETE FROM usuario WHERE id_usuario = ?");
            $stmt->bind_param("i", $id_usuario);

            if($stmt->execute())
                return true;
            return false;
        }

        //Todos los libros teoria
        public function getAllLibrosT($tematica){
            $stmt = $this->con->prepare("SELECT id_teoria, titulo, tematica, fecha, autor, portada, url_pdf FROM libro_teoria WHERE tematica = ?;");
            $stmt->bind_param("s",$tematica);
            $stmt->execute();
            $stmt->bind_result($id_teoria, $titulo, $tematica, $fecha, $autor, $portada, $url_pdf);
            
            $libros = array();

            while($stmt->fetch()){
                $libro = array();
                $libro['id_teoria']= $id_teoria;
                $libro['titulo']= $titulo;
                $libro['tematica']= $tematica;
                $libro['fecha']= $fecha;
                $libro['autor']= $autor;
                $libro['portada']= $portada;
                $libro['url_pdf']= $url_pdf;
    
                array_push($libros, $libro);
            }

            return $libros;
            
        }

        //Todos los ejercicios
        public function getAllEjerciciosT($tematica){
            $stmt = $this->con->prepare("SELECT id_ejercicios, titulo, tematica, fecha, autor, portada, url_pdf FROM libro_ejercicios WHERE tematica = ?;");
            $stmt->bind_param("s",$tematica);
            $stmt->execute();
            $stmt->bind_result($id_ejercicios, $titulo, $tematica, $fecha, $autor, $portada, $url_pdf);
            
            $ejercicios = array();

            while($stmt->fetch()){
                $ejercicio = array();
                $ejercicio['id_ejercicios']= $id_ejercicios;
                $ejercicio['titulo']= $titulo;
                $ejercicio['tematica']= $tematica;
                $ejercicio['fecha']= $fecha;
                $ejercicio['autor']= $autor;
                $ejercicio['portada']= $portada;
                $ejercicio['url_pdf']= $url_pdf;
    
                array_push($ejercicios, $ejercicio);
            }

            return $ejercicios;
            
        }

        //Todos los videos
        public function getAllVideosT($tematica){
            $stmt = $this->con->prepare("SELECT id_video, titulo, tematica, fecha, url_video FROM tuto_video WHERE tematica = ?;");
            $stmt->bind_param("s",$tematica);
            $stmt->execute();
            $stmt->bind_result($id_video, $titulo, $tematica, $fecha, $url_video);
            
            $ejercicios = array();

            while($stmt->fetch()){
                $ejercicio = array();
                $ejercicio['id_video']= $id_video;
                $ejercicio['titulo']= $titulo;
                $ejercicio['tematica']= $tematica;
                $ejercicio['fecha']= $fecha;
                $ejercicio['url_video']= $url_video;
    
                array_push($ejercicios, $ejercicio);
            }

            return $ejercicios;
            
        }

        //Todos los usuarios
        public function getAllUsers(){
            $stmt = $this->con->prepare("SELECT id_usuario, email, nombre_usuario, telefono, fecha FROM usuario;");
    
            $stmt->execute();
            $stmt->bind_result($id_usuario, $email, $nombre_usuario, $telefono, $fecha);
            
            $users = array();

            while($stmt->fetch()){
                $user = array();
                $user['id_usuario']= $id_usuario;
                $user['email']= $email;
                $user['nombre_usuario']= $nombre_usuario;
                $user['telefono']= $telefono;
                $user['fecha']= $fecha;
    
                array_push($users, $user);
            }

            return $users;
            
        }

        //Todos los usuarios
        public function getOneUser($emailuser){
            $stmt = $this->con->prepare("SELECT id_usuario, email, nombre_usuario, telefono, fecha FROM usuario WHERE email = ?;");
            $stmt->bind_param("s",$emailuser);
            $stmt->execute();
            $stmt->bind_result($id_usuario, $email, $nombre_usuario, $telefono, $fecha);
            
            $users = array();

            while($stmt->fetch()){
                $user = array();
                $user['id_usuario']= $id_usuario;
                $user['email']= $email;
                $user['nombre_usuario']= $nombre_usuario;
                $user['telefono']= $telefono;
                $user['fecha']= $fecha;
    
                array_push($users, $user);
            }

            return $users;
            
        }

        //Tras loggearse se pueden recoger datos
        public function getUserByEmail($email){
            $stmt = $this->con->prepare("SELECT id_usuario, email, nombre_usuario, telefono, fecha FROM usuario WHERE email = ?");
            $stmt->bind_param("s", $email);
            $stmt->execute();
            $stmt->bind_result($id_usuario, $email, $nombre_usuario, $telefono, $fecha);
            $stmt->fetch();
            $user = array();
            $user['id_usuario']= $id_usuario;
            $user['email']= $email;
            $user['nombre_usuario']= $nombre_usuario;
            $user['telefono']= $telefono;
            $user['fecha']= $fecha;

            return $user;
        }

        //Conseguir la pass del user
        private function getUsersPasswordByEmail($email){
            $stmt = $this->con->prepare("SELECT password FROM usuario WHERE email = ?");
            $stmt->bind_param("s", $email);
            $stmt->execute();
            $stmt->bind_result($password);
            $stmt->fetch();
            return $password;
        }

        public function getUserExist($email){

            $stmt = $this->con->prepare("SELECT id_usuario, email, nombre_usuario, telefono FROM usuario WHERE email = ?;");
            $stmt->bind_param("s", $email);
            $stmt->execute();
            $stmt->bind_result($id_usuario, $email, $nombre_usuario, $telefono);
            $stmt->fetch();

            $user = array();
            $user['id_usuario']= $id_usuario;
            $user['email']= $email;
            $user['nombre_usuario']= $nombre_usuario;
            $user['telefono']= $telefono;

            return $user;  

        }

         //GUARDAR MENSAJES
         public function saveMessage($ip_usuario, $nombre_usuario, $email, $mensaje){

            if($this->isEmailExist($email) && $this->isNomUserExist($nombre_usuario)){

                $stmt = $this->con->prepare("INSERT INTO foro (ip_usu, id_usuario, nombre_usuario, email, mensaje, fecha) VALUES (?, ?, ?, ?, ?, ?)");
                $fecha = date('d/m/Y G:i'); //fecha sistema
                $id_usuario = $this->getIdUser($nombre_usuario, $email);

                // Store the cipher method 
                    $ciphering = "AES-128-CTR"; 
                    
                    // Use OpenSSl Encryption method 
                    $iv_length = openssl_cipher_iv_length($ciphering); 
                    $options = 0; 
                    
                    // Non-NULL Initialization Vector for encryption 
                    $encryption_iv = '1234567891011121'; 
                    
                    // Store the encryption key 
                    $encryption_key = "keykeykey"; 
                    
                    // Use openssl_encrypt() function to encrypt the data 
                    $encryptionMessage = openssl_encrypt($mensaje, $ciphering, 
                                $encryption_key, $options, $encryption_iv); 

                    $encryptionIP = openssl_encrypt($ip_usuario, $ciphering, 
                                $encryption_key, $options, $encryption_iv); 


                $stmt->bind_param("ssssss", $encryptionIP, $id_usuario, $nombre_usuario, $email, $encryptionMessage, $fecha);

                if($stmt->execute()){
                    return USER_CREATED; 
                }else{
                    return USER_FAILURE;
                }

           }

           return USER_EXISTS; 

        }

        //GUARDAR AMIGO
        public function saveAmigo($nombre_user, $nombre_user_amigo){

            if($this->isNomUserExist($nombre_user) && $this->isNomUserExist($nombre_user_amigo) && ($nombre_user != $nombre_user_amigo)){

                if(!$this->isAmigoExist($nombre_user, $nombre_user_amigo)){

                    $stmt = $this->con->prepare("INSERT INTO amigos (nombre_user, nombre_user_amigo) VALUES (?, ?)");

                    $stmt->bind_param("ss", $nombre_user, $nombre_user_amigo);

                    if($stmt->execute()){
                        return USER_CREATED; 
                    }else{
                        return USER_FAILURE;
                    }

                }

           }

           return USER_EXISTS; 

        }


        
        //El id del usuario
        public function getIdUser($nombre_usuario, $email){
            $stmt = $this->con->prepare("SELECT id_usuario FROM usuario WHERE nombre_usuario = ? AND email = ?;");
            $stmt->bind_param("ss",$nombre_usuario, $email);
            $stmt->execute();
            $stmt->bind_result($id_usuario);
            
            $stmt->fetch();
            
            return $id_usuario;
            
        }

        //Todos los usuarios
        public function getAllMessages(){
            $stmt = $this->con->prepare("SELECT ip_usu, nombre_usuario, email, mensaje, fecha FROM foro;");
    
            $stmt->execute();
            $stmt->bind_result($ip_usu, $nombre_usuario, $email, $mensaje, $fecha);
            
            $messages = array();

            while($stmt->fetch()){
                $message = array();
                $message['ip_usu']= $this->decryptMessage($ip_usu);
                $message['nombre_usuario']= $nombre_usuario;
                $message['email']= $email;
                $message['mensaje']= $this->decryptMessage($mensaje);
                $message['fecha']= $fecha;
    
                array_push($messages, $message);
            }

            return $messages;
            
        }

        //Todos los amigos
        public function getAllFriends($nombre_user){
            $stmt = $this->con->prepare("SELECT id_usuario, email, nombre_usuario, telefono, fecha FROM usuario WHERE nombre_usuario IN (SELECT nombre_user_amigo FROM amigos WHERE nombre_user = ?);");
            $stmt->bind_param("s", $nombre_user);
            $stmt->execute();
            $stmt->bind_result($id_usuario, $email, $nombre_usuario, $telefono, $fecha);
            
            $messages = array();

            while($stmt->fetch()){
                $message = array();
                $message['id_usuario']= $id_usuario;
                $message['email']= $email;
                $message['nombre_usuario']= $nombre_usuario;
                $message['telefono']= $telefono;
                $message['fecha']= $fecha;
    
    
                array_push($messages, $message);
            }

            return $messages;
            
        }

        public function decryptMessage($encryption){

            // Store the cipher method 
            $ciphering = "AES-128-CTR"; 
                    
            // Use OpenSSl Encryption method 
            $iv_length = openssl_cipher_iv_length($ciphering); 
            $options = 0; 

            // Non-NULL Initialization Vector for decryption 
            $decryption_iv = '1234567891011121'; 
            
            // Store the decryption key 
            $decryption_key = "keykeykey"; 
            
            // Use openssl_decrypt() function to decrypt the data 
            $decryption=openssl_decrypt ($encryption, $ciphering,  
                    $decryption_key, $options, $decryption_iv); 


            return $decryption;

        }

        //Comprobar si existe
        public function isEmailExist($email){
            $stmt = $this->con->prepare("SELECT id_usuario FROM usuario WHERE email = ?");
            $stmt->bind_param("s", $email);
            $stmt->execute(); 
            $stmt->store_result(); 
            return $stmt->num_rows > 0;  
        }

        //Comprobar si existe user con este nombre
        private function isNomUserExist($nombre_usuario){
            $stmt = $this->con->prepare("SELECT id_usuario FROM usuario WHERE nombre_usuario = ?");
            $stmt->bind_param("s", $nombre_usuario);
            $stmt->execute(); 
            $stmt->store_result(); 
            return $stmt->num_rows > 0;   
        }

        //Comprobar si existe user con este nombre
        private function isAmigoExist($nombre_user, $nombre_user_amigo){
            $stmt = $this->con->prepare("SELECT id_amigos FROM amigos WHERE nombre_user = ? AND nombre_user_amigo = ?");
            $stmt->bind_param("ss", $nombre_user, $nombre_user_amigo);
            $stmt->execute(); 
            $stmt->store_result(); 
            return $stmt->num_rows > 0;  
        }

    }