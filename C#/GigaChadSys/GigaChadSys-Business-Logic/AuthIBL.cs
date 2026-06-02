using GigaChadSysModel;

namespace GigaChadSysBL;

public interface AuthIBL
{
    Usuario? Login(string email, string contrasenia);
}