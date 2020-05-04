package ca.qc.bdeb.voyagr.Modele;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Permet de gérer la connexion avec Firebase et de gérer l'authentification des utilisateurs
 */
public class GestionFirebase {

    private String nomUtilisateur, emailUtilisateur;
    private static FirebaseAuth accesFirebase;
    private static FirebaseUser utilisateurFirebase;
    private static boolean  emailVerificationEnvoye = false, emailMDPEnvoye = false;
    private final static long constanteEntreEmail = 60000;
    private static long tempsEnvoieEmail = 0;
    private static String etatEmail;


    /**
     * Permet d'envoyer un email de vérification à l'utilisateur
     *
     * @return un booléen vrai si le email a été envoyé
     */
    public boolean envoyerEmailVerification() {
        emailVerificationEnvoye = false;

        if (utilisateurFirebase != null) {
            System.out.println("email envoyer");
            utilisateurFirebase.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        if (System.currentTimeMillis() - constanteEntreEmail >= tempsEnvoieEmail) {
                            emailVerificationEnvoye = true;
                            tempsEnvoieEmail = System.currentTimeMillis();
                        } else {
                            emailVerificationEnvoye = false;
                        }
                    }
                }
            });
        }
        return emailVerificationEnvoye;
    }

    /**
     * Permet de déconnecter un utilisateur en cours
     */
    public void deconnexion() {
        accesFirebase.signOut();
        utilisateurFirebase = null;
    }

    /**
     * permet de vérifier si l'utilisateur a déjà vérifié son email
     *
     * @return un booléen si le email a été vérifié
     */
    public boolean isUtilisateurVerifier() {
        FirebaseUser utilisateurAVerifier = utilisateurFirebase;
        Boolean emailVerifier = utilisateurAVerifier.isEmailVerified();
        return emailVerifier;
    }


    /**
     * Permet d'envoyer un email de réinitialisation de mot de passe
     *
     * @param email email auquel le système essaie d'envoyer un email pour le changement du mot de passe
     * @return un booléen si le mot de passe a été envoyé
     */
    public static void envoyerEmailMotDePasse(String email) {
        emailMDPEnvoye = false;

        accesFirebase.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                emailMDPEnvoye = true;
                if (Modele.getLangueFrancais()) {
                    etatEmail = "Le email a été envoyé";
                } else {
                    etatEmail = "Ein E-Mail wurde geschisckt";
                }
            }

        });
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (!emailMDPEnvoye) {
                    if (Modele.getLangueFrancais()) {
                        etatEmail = "Le email n'a pas de compte associé. Veuillez réessayer";
                    } else {
                        etatEmail = "Es gibt kein Konto mit dieser E-Mail";
                    }
                }
            }
        }, 2000);


    }

    public static void setAccesFirebase(FirebaseAuth accesFirebase) {
        GestionFirebase.accesFirebase = accesFirebase;
    }

    public static void setUtilisateurFirebase(FirebaseUser utilisateurFirebase) {
        GestionFirebase.utilisateurFirebase = utilisateurFirebase;
    }

    public static FirebaseAuth getAccesFirebase() {
        return accesFirebase;
    }

    public static FirebaseUser getUtilisateurFirebase() {
        return utilisateurFirebase;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public void setEmailUtilisateur(String emailUtilisateur) {
        this.emailUtilisateur = emailUtilisateur;
    }

    public static String getEtatEmail() {
        return etatEmail;
    }
}
