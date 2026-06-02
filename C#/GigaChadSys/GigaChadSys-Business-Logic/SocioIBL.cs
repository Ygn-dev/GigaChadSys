using GigaChadSysModel;

namespace GigaChadSysBL;

public interface SocioIBL
{
    List<Socio> ListAll();
    Socio? Load(int id);
    Socio? LoadByEmail(string email);
    Socio Save(Socio socio);
    Socio? Update(Socio socio);
    void Remove(Socio socio);
}